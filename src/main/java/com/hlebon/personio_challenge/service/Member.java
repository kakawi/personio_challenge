package com.hlebon.personio_challenge.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter
public class Member {

    private final String name;

    private Member supervisor;

    private Collection<Member> employees = new LinkedList<>();

    public Member(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        }
        this.name = name;
    }

    public void addEmployee(Member employee) {
        employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return name.equals(member.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
