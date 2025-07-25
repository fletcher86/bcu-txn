package org.fletcher.repository

import org.fletcher.model.BcuTxn
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BcuTxnMongoRepository : MongoRepository<BcuTxn, String>