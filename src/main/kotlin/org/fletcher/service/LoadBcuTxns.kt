package org.fletcher.service

import org.fletcher.entity.BcuTxnEntity
import org.fletcher.model.BcuTxn
import org.fletcher.repository.BcuTxnEntityRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoadBcuTxns(
    private val repo: BcuTxnEntityRepository
) {
    private val log = LoggerFactory.getLogger(LoadBcuTxns::class.java)

    fun loadBcuTxns(bcuTxns: List<BcuTxn>) {
        for (txn in bcuTxns) {
            val e = BcuTxnEntity(
                txn.transactionId ?: "",
                txn.accountId,
                txn.date,
                txn.name,
                txn.description,
                txn.checkNumber,
                txn.category,
                txn.amount,
                txn.balance
            )
            val eFromDb = repo.findById(e.transactionId)

            eFromDb.ifPresent { t ->
                if (t.amount != e.amount) {
                    log.info("Txn Amount Differs {}", e.transactionId)
                }
                if (t.balance != e.balance) {
                    log.info("Txn Balance Differs {}", e.transactionId)
                }
            }
            eFromDb.ifPresentOrElse(
                { t -> log.info("Transaction already exists, skipping save for txn: [{}]", t.transactionId) },
                {
                    log.info("Transaction does not exist, saving txn to database [{}]", txn.transactionId)
                    repo.save(e)
                }
            )
        }
    }
}