package com.votehaeduo.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteCreateOption {

    MANUAL("투표 수동 생성"),
    AUTO("투표 자동 생성");

    private final String name;

}
