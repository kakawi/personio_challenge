package com.hlebon.personio_challenge.service;

import lombok.Getter;

@Getter
public class Member {

    private final String name;

    public Member(String name) {
        this.name = name;
    }

}
