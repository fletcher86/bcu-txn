package org.fletcher.service

import org.fletcher.entity.BcuTxnEntity
import org.fletcher.model.BcuTxn
import org.fletcher.repository.BcuTxnEntityRepository
import org.fletcher.repository.BcuTxnMongoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoadBcuTxns(
    private val repo: BcuTxnEntityRepository,
    private val mongoRepo: BcuTxnMongoRepository
) {
    private val log = LoggerFactory.getLogger(LoadBcuTxns::class.java)

    fun loadBcuTxns(bcuTxns: List<BcuTxn>) {
        for (txn in bcuTxns) {
            val e = BcuTxnEntity(
                txn.id ?: "",
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
//            val meFromDb = mongoRepo.findById(e.transactionId)

//            meFromDb.ifPresentOrElse(
//                { t -> log.info("Mongo Transaction already exists, skipping save for txn: [{}]", t.id) },
//                {
//                    log.info("Mongo Transaction does not exist, saving txn to database [{}]", txn.id)
//                    mongoRepo.save(txn)
//                }
//            )


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
                    log.info("Transaction does not exist, saving txn to database [{}]", txn.id)
                    repo.save(e)
                }
            )
        }
    }
}