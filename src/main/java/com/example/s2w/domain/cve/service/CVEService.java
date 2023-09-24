package com.example.s2w.domain.cve.service;

import com.example.s2w.domain.cve.dto.CVEDTO.ReadCVEResponse;

public interface CVEService {

    ReadCVEResponse ReadCVEList(String seedId);

}
