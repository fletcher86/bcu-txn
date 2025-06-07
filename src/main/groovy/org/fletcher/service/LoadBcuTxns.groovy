package org.fletcher.service

import groovy.util.logging.Slf4j
import org.fletcher.entity.BcuTxnEntity
import org.fletcher.model.BcuTxn
import org.fletcher.repository.BcuTxnEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class LoadBcuTxns {

    @Autowired
    private final BcuTxnEntityRepository repo

    void loadBcuTxns(List<BcuTxn> bcuTxns) {
        for (BcuTxn txn : bcuTxns) {
            BcuTxnEntity e = new BcuTxnEntity(
                    transactionId: txn.transactionId,
                    accountId: txn.accountId,
                    date: txn.date,
                    name: txn.name,
                    description: txn.description,
                    checkNumber: txn.checkNumber,
                    category: txn.category,
                    amount: txn.amount,
                    balance: txn.balance
            )
            Optional<BcuTxnEntity> e2 = repo.findById(e.transactionId)

            e2.ifPresent { t1 ->
                if (t1.amount != e.amount) {
                    log.info("Txn Amount Differs", e.transactionId)
                }
                if (t1.balance != e.balance) {
                    log.info("Txn Balance Differs", e.transactionId)
                }
            }
            e2.ifPresentOrElse({
                log.info("Transaction already exists, skipping save")
            }, {
                log.info("Transaction does not exist, saving")
                repo.save(e)
            })
        }
    }
}
