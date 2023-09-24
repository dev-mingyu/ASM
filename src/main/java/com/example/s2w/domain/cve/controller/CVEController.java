package com.example.s2w.domain.cve.controller;

import com.example.s2w.domain.cve.service.CVEService;
import com.example.s2w.domain.global.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Result> createSeed() {

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Result.successResult(null));
    }
}
