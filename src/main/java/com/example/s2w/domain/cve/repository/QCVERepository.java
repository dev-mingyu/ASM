package com.example.s2w.domain.cve.repository;

import com.example.s2w.domain.cve.model.CVE;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QCVERepository {

    Page<CVE> getCVEList(List<String> softwares, Pageable pageable);
}
