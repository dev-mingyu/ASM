package com.example.s2w.domain.seed.repository;

import static com.example.s2w.domain.seed.model.QSeed.seed;

import com.example.s2w.domain.global.enumeration.GlobalYn;
import com.example.s2w.domain.seed.model.Seed;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QSeedRepositoryImpl implements QSeedRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Seed> findBySeedIdWithPageable(String seedId, Pageable pageable) {
        List<Seed> seedList = jpaQueryFactory.selectFrom(seed)
                                             .leftJoin(seed.softwares)
                                             .fetchJoin()
                                             .where(seed.seedId.eq(seedId)
                                                               .and(seed.deleteYn.eq(GlobalYn.N)))
                                             .offset(pageable.getOffset())
                                             .limit(pageable.getPageSize())
                                             .fetch();

        int total = jpaQueryFactory.selectFrom(seed)
                                   .where(seed.seedId.eq(seedId)
                                                     .and(seed.deleteYn.eq(GlobalYn.N)))
                                   .fetch()
                                   .size();

        return new PageImpl<>(seedList, pageable, total);

    }


}
