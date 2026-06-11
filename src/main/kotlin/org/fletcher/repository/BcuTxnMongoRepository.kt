package org.fletcher.repository

import org.fletcher.model.BcuTxn
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(name = ["spring.data.mongodb.enabled"], havingValue = "true")
interface BcuTxnMongoRepository : MongoRepository<BcuTxn, String>