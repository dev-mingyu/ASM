package com.example.s2w.domain.cve.controller;

import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponseWithPageable;
import com.example.s2w.domain.cve.service.CVEService;
import com.example.s2w.domain.global.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cve")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CVEController {

    private final CVEService cveService;

    @GetMapping("/seeds/{seedId}")
    public ResponseEntity<Result> createSeed(@PathVariable("seedId") String seedId, Pageable pageable) {
        ReadCVEResponseWithPageable readCVEResponseWithPageable = cveService.ReadCVEList(seedId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(Result.successResult(readCVEResponseWithPageable));
    }
}
