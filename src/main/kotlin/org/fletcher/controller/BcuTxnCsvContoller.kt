package org.fletcher.controller

import com.opencsv.bean.CsvToBeanBuilder
import org.fletcher.model.BcuTxn
import org.fletcher.service.LoadBcuTxns
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.InputStreamReader

@RestController
class BcuTxnCsvContoller @Autowired constructor(
    private val loadBcuTxns: LoadBcuTxns
) {
    private val log = LoggerFactory.getLogger(BcuTxnCsvContoller::class.java)

    @PostMapping(
        value = ["/upload-csv"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadCsv(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            if (file.isEmpty) {
                throw IllegalArgumentException("File is empty")
            }

            val reader = InputStreamReader(file.inputStream)
            val bcuTxns: List<BcuTxn> = CsvToBeanBuilder<BcuTxn>(reader)
                .withType(BcuTxn::class.java)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse()

            loadBcuTxns.loadBcuTxns(bcuTxns)
            ResponseEntity.ok("File uploaded successfully")
        } catch (e: Exception) {
            log.info("Error uploading file", e)
            ResponseEntity.badRequest().body(e.message)
        }
    }
}