package com.example.s2w.domain.global.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeedStatus {

    active("활성"),
    inactive("비활성")
    ;

    private final String description;
}
