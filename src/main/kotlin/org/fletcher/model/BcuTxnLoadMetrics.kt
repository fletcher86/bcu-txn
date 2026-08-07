package org.fletcher.model

data class BcuTxnAccountMetrics(
    val txnsAlreadyExisted: Int = 0,
    val txnsSaved: Int = 0
)

data class BcuTxnLoadMetrics(
    val mysql: Map<String, BcuTxnAccountMetrics>,
    val mongo: Map<String, BcuTxnAccountMetrics>,
    val mongoEnabled: Boolean
) {
    companion object {
        fun fromCounts(
            mysqlExistingByAccount: Map<String, Int>,
            mysqlSavedByAccount: Map<String, Int>,
            mongoExistingByAccount: Map<String, Int>,
            mongoSavedByAccount: Map<String, Int>,
            mongoEnabled: Boolean
        ): BcuTxnLoadMetrics {
            return BcuTxnLoadMetrics(
                mysql = toAccountMetricsMap(mysqlExistingByAccount, mysqlSavedByAccount),
                mongo = toAccountMetricsMap(mongoExistingByAccount, mongoSavedByAccount),
                mongoEnabled = mongoEnabled
            )
        }

        private fun toAccountMetricsMap(
            existing: Map<String, Int>,
            saved: Map<String, Int>
        ): Map<String, BcuTxnAccountMetrics> {
            return (existing.keys + saved.keys)
                .sorted()
                .associateWith { accountId ->
                    BcuTxnAccountMetrics(
                        txnsAlreadyExisted = existing[accountId] ?: 0,
                        txnsSaved = saved[accountId] ?: 0
                    )
                }
        }
    }
}

data class BcuTxnMetricsLogPayload(
    val files: List<String>,
    val mongo: Map<String, BcuTxnAccountMetrics>,
    val mysql: Map<String, BcuTxnAccountMetrics>
)

class BcuTxnMetricsAccumulator {
    private val mysqlExistingByAccount = mutableMapOf<String, Int>()
    private val mysqlSavedByAccount = mutableMapOf<String, Int>()
    private val mongoExistingByAccount = mutableMapOf<String, Int>()
    private val mongoSavedByAccount = mutableMapOf<String, Int>()

    fun add(metrics: BcuTxnLoadMetrics) {
        merge(metrics.mysql, mysqlExistingByAccount, mysqlSavedByAccount)
        if (metrics.mongoEnabled) {
            merge(metrics.mongo, mongoExistingByAccount, mongoSavedByAccount)
        }
    }

    fun toLogPayload(files: List<String>): BcuTxnMetricsLogPayload {
        return BcuTxnMetricsLogPayload(
            files = files,
            mongo = toAccountMetricsMap(mongoExistingByAccount, mongoSavedByAccount),
            mysql = toAccountMetricsMap(mysqlExistingByAccount, mysqlSavedByAccount)
        )
    }

    private fun merge(
        source: Map<String, BcuTxnAccountMetrics>,
        existingTarget: MutableMap<String, Int>,
        savedTarget: MutableMap<String, Int>
    ) {
        source.forEach { (accountId, metrics) ->
            existingTarget[accountId] = (existingTarget[accountId] ?: 0) + metrics.txnsAlreadyExisted
            savedTarget[accountId] = (savedTarget[accountId] ?: 0) + metrics.txnsSaved
        }
    }

    private fun toAccountMetricsMap(
        existing: Map<String, Int>,
        saved: Map<String, Int>
    ): Map<String, BcuTxnAccountMetrics> {
        return (existing.keys + saved.keys)
            .sorted()
            .associateWith { accountId ->
                BcuTxnAccountMetrics(
                    txnsAlreadyExisted = existing[accountId] ?: 0,
                    txnsSaved = saved[accountId] ?: 0
                )
            }
    }
}
