package com.example.s2w.domain.cve.model;

import com.example.s2w.domain.global.model.BaseTime;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "cve")
public class CVE extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Comment("pk")
    private Long id;

    @Column(nullable = false, length = 30)
    private String cveCode;

    @ElementCollection
    @CollectionTable(name = "cve_softwares", joinColumns = @JoinColumn(name = "cve_id"))
    @Column(name = "software")
    private List<String> softwares;
}
