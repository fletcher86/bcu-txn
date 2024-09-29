package org.fletcher.controller

import com.opencsv.bean.CsvToBeanBuilder
import groovy.util.logging.Slf4j
import org.fletcher.model.BcuTxn
import org.fletcher.service.LoadBcuTxns
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Slf4j
class BcuTxnCsvContoller {

    @Autowired
    private final LoadBcuTxns loadBcuTxns

    @PostMapping(value = "/upload-csv",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty")
            }

            Reader reader = new InputStreamReader(file.inputStream)
            List<BcuTxn> bcuTxns = new CsvToBeanBuilder<BcuTxn>(reader)
                    .withType(BcuTxn.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse()

            loadBcuTxns.loadBcuTxns(bcuTxns)
            return ResponseEntity.ok().body("File uploaded successfully")
        } catch (Exception e) {
            log.info("Error uploading file", e)
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}