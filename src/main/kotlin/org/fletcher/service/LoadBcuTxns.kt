package org.fletcher.service

import org.fletcher.entity.BcuTxnEntity
import org.fletcher.model.BcuTxn
import org.fletcher.model.BcuTxnLoadMetrics
import org.fletcher.repository.BcuTxnEntityRepository
import org.fletcher.repository.BcuTxnMongoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoadBcuTxns(
    private val repo: BcuTxnEntityRepository,
    private val mongoRepo: Optional<BcuTxnMongoRepository>
) {
    private val log = LoggerFactory.getLogger(LoadBcuTxns::class.java)

    fun loadBcuTxns(bcuTxns: List<BcuTxn>): BcuTxnLoadMetrics {
        // Map and validate all incoming records up front
        val incoming: List<BcuTxnEntity> = bcuTxns.map { txn ->
            BcuTxnEntity(
                txn.id ?: throw IllegalArgumentException("Transaction ID is missing for record: $txn"),
                txn.accountId,
                txn.date,
                txn.name,
                txn.description,
                txn.checkNumber,
                txn.category,
                txn.amount,
                txn.balance
            )
        }

        val ids = incoming.map { it.transactionId }

        // Single bulk fetch for MySQL — no N+1
        val existingById: Map<String, BcuTxnEntity> = repo.findAllById(ids).associateBy { it.transactionId }

        val toSave = mutableListOf<BcuTxnEntity>()
        val mysqlExistingByAccount = mutableMapOf<String, Int>()
        val mysqlSavedByAccount = mutableMapOf<String, Int>()
        for (e in incoming) {
            val accountId = accountKey(e.accountId)
            val existing = existingById[e.transactionId]
            if (existing != null) {
                if (existing.amount != e.amount) log.info("Txn Amount Differs {}", e.transactionId)
                if (existing.balance != e.balance) log.info("Txn Balance Differs {}", e.transactionId)
                log.info("Transaction already exists, skipping save for txn: [{}]", e.transactionId)
                incrementCount(mysqlExistingByAccount, accountId)
            } else {
                log.info("Transaction does not exist, queuing save for txn: [{}]", e.transactionId)
                toSave.add(e)
                incrementCount(mysqlSavedByAccount, accountId)
            }
        }

        if (toSave.isNotEmpty()) {
            repo.saveAll(toSave)
        }

        // Single bulk fetch + save for Mongo — no N+1
        val mongoEnabled = mongoRepo.isPresent
        val mongoExistingByAccount = mutableMapOf<String, Int>()
        val mongoSavedByAccount = mutableMapOf<String, Int>()
        mongoRepo.ifPresent { mongo ->
            val existingMongoIds: Set<String> = mongo.findAllById(ids).mapNotNull { it.id }.toSet()
            val mongoToSave = bcuTxns.filter { it.id != null && it.id !in existingMongoIds }

            bcuTxns.forEach { txn ->
                val accountId = accountKey(txn.accountId)
                if (txn.id in existingMongoIds) {
                    incrementCount(mongoExistingByAccount, accountId)
                } else {
                    incrementCount(mongoSavedByAccount, accountId)
                }
            }

            if (mongoToSave.isNotEmpty()) {
                mongoToSave.forEach { log.info("Mongo Transaction does not exist, saving txn: [{}]", it.id) }
                mongo.saveAll(mongoToSave)
            } else {
                log.info("All {} Mongo transactions already exist, nothing to save", ids.size)
            }
        }

        return BcuTxnLoadMetrics.fromCounts(
            mysqlExistingByAccount = mysqlExistingByAccount,
            mysqlSavedByAccount = mysqlSavedByAccount,
            mongoExistingByAccount = mongoExistingByAccount,
            mongoSavedByAccount = mongoSavedByAccount,
            mongoEnabled = mongoEnabled
        )
    }

    private fun accountKey(accountId: String?): String = accountId ?: "UNKNOWN_ACCOUNT_ID"

    private fun incrementCount(counts: MutableMap<String, Int>, key: String) {
        counts[key] = (counts[key] ?: 0) + 1
    }
}
