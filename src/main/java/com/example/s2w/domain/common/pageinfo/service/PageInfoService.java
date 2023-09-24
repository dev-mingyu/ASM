package com.example.s2w.domain.common.pageinfo.service;

import com.example.s2w.domain.common.pageinfo.dto.PageDTO.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageInfoService {

    <T> PageResponse getPageInfo(Pageable pageable, Page<T> allWithPaging);

     <T extends PageResponse> T setPageableData(T response, Page<?> pageData);

}
