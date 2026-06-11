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
    fun uploadCsv(@RequestParam("files") files: List<MultipartFile>): ResponseEntity<String> {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("No files provided")
        }

        val errors = mutableListOf<String>()
        var totalLoaded = 0

        for (file in files) {
            try {
                if (file.isEmpty) {
                    errors.add("${file.originalFilename}: file is empty")
                    continue
                }

                val bcuTxns: List<BcuTxn> = InputStreamReader(file.inputStream).use { reader ->
                    CsvToBeanBuilder<BcuTxn>(reader)
                        .withType(BcuTxn::class.java)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build()
                        .parse()
                }

                loadBcuTxns.loadBcuTxns(bcuTxns)
                totalLoaded += bcuTxns.size
                log.info("Loaded ${bcuTxns.size} records from ${file.originalFilename}")
            } catch (e: Exception) {
                log.error("Error uploading file ${file.originalFilename}", e)
                errors.add("${file.originalFilename}: failed to process file")
            }
        }

        return if (errors.isEmpty()) {
            ResponseEntity.ok("$totalLoaded records loaded successfully from ${files.size} file(s)")
        } else {
            val message = buildString {
                if (totalLoaded > 0) appendLine("$totalLoaded records loaded successfully.")
                appendLine("Errors:")
                errors.forEach { appendLine("  - $it") }
            }
            ResponseEntity.badRequest().body(message.trim())
        }
    }
}