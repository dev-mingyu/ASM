package com.example.s2w.domain.seed.repository;

import static com.example.s2w.domain.seed.model.QSeed.seed;
import static com.example.s2w.domain.seed.model.QSeedSoftware.seedSoftware;

import com.example.s2w.domain.global.enumeration.GlobalYn;
import com.example.s2w.domain.seed.model.SeedSoftware;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QSeedSoftwareRepositoryImpl implements QSeedSoftwareRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SeedSoftware> findBySeedIdWithPageable(String seedId, Pageable pageable) {
        List<Long> idList = jpaQueryFactory.select(seed.id)
                                           .from(seed)
                                           .where(seed.seedId.eq(seedId)
                                                             .and(seed.deleteYn.eq(GlobalYn.N)))
                                           .fetch();

        List<SeedSoftware> seedSoftwareList = jpaQueryFactory.selectFrom(seedSoftware)
                                                  .where(seed.id.in(idList))
                                                  .orderBy(seedSoftware.id.asc())
                                                  .offset(pageable.getOffset())
                                                  .limit(pageable.getPageSize())
                                                  .fetch();

        int total = jpaQueryFactory.selectFrom(seedSoftware)
                                   .where(seed.id.in(idList))
                                   .fetch().size();

        return new PageImpl<> (seedSoftwareList, pageable, total);
    }
}
