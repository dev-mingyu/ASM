package com.example.s2w.domain.common.pageinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class PageDTO {

    @SuperBuilder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageResponse {
        private long totalElements;
        private int numberOfElements;
        private int currentPage;
        private int totalPages;
        private int size;
        private boolean first;
        private boolean last;

    }
}
