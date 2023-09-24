package com.example.s2w.domain.seed.model;

import static com.example.s2w.domain.common.utils.Pattern.IPV4_PATTERN;
import static com.example.s2w.domain.common.utils.Pattern.SEED_ID_PATTERN;

import com.example.s2w.domain.global.enumeration.SeedStatus;
import com.example.s2w.domain.global.exception.type.InvalidRequestException;
import com.example.s2w.domain.global.model.BaseTime;
import com.example.s2w.domain.global.response.ErrorCode;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuperBuilder
@DynamicInsert
@DynamicUpdate
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_seed")
public class Seed extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("pk")
    private Long id;

    @Column(nullable = false, length = 30)
    private String seedId;

    @Column(nullable = false)
    private String subDomain;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SeedStatus status;

    @Column(nullable = false, length = 15)
    private String ip;

    @OneToMany(mappedBy = "seed", fetch = FetchType.LAZY)
    private List<SeedSoftware> softwares;

    @PrePersist
    public void validAndStandardSeedId() {
        validSeedId();
        validIp();

        this.seedId = this.seedId.substring(0, 4).toUpperCase() + this.seedId.substring(4);

    }

    private void validSeedId() {
        if (this.seedId == null || this.seedId.length() != 15) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }

        if (!this.seedId.matches(SEED_ID_PATTERN)) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }

    private void validIp() {
        if (this.ip == null || !this.ip.matches(IPV4_PATTERN)) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST);
        }
    }


}
