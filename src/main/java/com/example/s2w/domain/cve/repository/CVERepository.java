package com.example.s2w.domain.cve.repository;

import com.example.s2w.domain.cve.model.CVE;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVERepository extends JpaRepository<CVE, Long> {

}
