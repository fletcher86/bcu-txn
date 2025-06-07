package org.fletcher.repository

import org.fletcher.entity.BcuTxnEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BcuTxnEntityRepository : JpaRepository<BcuTxnEntity, String>