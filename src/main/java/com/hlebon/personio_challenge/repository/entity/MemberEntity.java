package com.hlebon.personio_challenge.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "members")
@Getter
@Setter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne
    @JoinTable(name = "company",
            joinColumns = { @JoinColumn(name = "employee_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "supervisor_id", referencedColumnName = "id") })
    private MemberEntity supervisor;
}
