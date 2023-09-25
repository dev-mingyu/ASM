package com.example.s2w.domain.cve.dto;

import com.example.s2w.domain.common.pageinfo.dto.PageDTO.PageResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class CVEDTO {

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadCVEResponseWithPageable extends PageResponse {
        private List<ReadCVEResponse> contents;
    }

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadCVEResponse {
        private String cveCode;
        private String software;
    }

}
