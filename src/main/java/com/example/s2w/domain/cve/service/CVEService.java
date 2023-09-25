package com.example.s2w.domain.cve.service;

import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponseWithPageable;
import org.springframework.data.domain.Pageable;

public interface CVEService {

    ReadCVEResponseWithPageable ReadCVEList(String seedId, Pageable pageable);

}
