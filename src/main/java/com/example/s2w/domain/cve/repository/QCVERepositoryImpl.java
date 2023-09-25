package com.example.s2w.domain.cve.repository;

import static com.example.s2w.domain.cve.model.QCVE.cVE;

import com.example.s2w.domain.cve.model.CVE;
import com.example.s2w.domain.global.enumeration.GlobalYn;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QCVERepositoryImpl implements QCVERepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CVE> getCVEList(List<String> softwares, Pageable pageable) {

        List<CVE> cveList = jpaQueryFactory.selectFrom(cVE)
                                           .where(cVE.software.in(softwares)
                                                              .and(cVE.deleteYn.eq(GlobalYn.N)))
                                           .offset(pageable.getOffset())
                                           .limit(pageable.getPageSize())
                                           .fetch();

        int total = jpaQueryFactory.selectFrom(cVE)
                                   .where(cVE.software.in(softwares)
                                                      .and(cVE.deleteYn.eq(GlobalYn.N)))
                                   .fetch()
                                   .size();
        return new PageImpl<>(cveList, pageable, total);
    }
}
