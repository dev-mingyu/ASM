package com.example.s2w.domain.common.pageinfo.service;

import com.example.s2w.domain.common.pageinfo.dto.PageDTO.PageResponse;
import com.example.s2w.domain.global.exception.type.InvalidRequestException;
import com.example.s2w.domain.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PageInfoServiceImpl implements PageInfoService {

    @Override
    public <T> PageResponse getPageInfo(Pageable pageable, Page<T> allWithPaging) {
        return PageResponse.builder()
                           .totalElements(allWithPaging.getTotalElements())
                           .numberOfElements(allWithPaging.getNumberOfElements())
                           .currentPage(allWithPaging.getNumber())
                           .totalPages(allWithPaging.getTotalPages())
                           .first(allWithPaging.isFirst())
                           .last(allWithPaging.isLast())
                           .build();
    }

    @Override
    public <T extends PageResponse> T setPageableData(T response, Page<?> pageData) {

        if(pageData.getTotalPages() < pageData.getNumber() +1) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }

        response.setTotalElements(pageData.getTotalElements());
        response.setNumberOfElements(pageData.getNumberOfElements());
        response.setCurrentPage(pageData.getNumber() + 1);
        response.setTotalPages(pageData.getTotalPages());
        response.setFirst(pageData.isFirst());
        response.setLast(pageData.isLast());
        response.setSize(pageData.getSize());
        return response;
    }

}
