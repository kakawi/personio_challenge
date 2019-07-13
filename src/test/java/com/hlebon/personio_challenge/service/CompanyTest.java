package com.hlebon.personio_challenge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(BlockJUnit4ClassRunner.class)
public class CompanyTest {

    @Test
    public void unitMembers_success() throws Exception {
        Company company = new Company();
        Member employee = new Member("employee");
        Member supervisor = new Member("supervisor");
        company.unitMembers(employee, supervisor);

        Member root = company.getRoot();

        // assert
        assertNotNull(root);
    }

    @Test(expected = ServiceException.class)
    public void unitMembers_loop() throws Exception {
        Company company = new Company();
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");
        company.unitMembers(member1, member2);
        company.unitMembers(member2, member3);
        company.unitMembers(member3, member1);
    }

    @Test(expected = ServiceException.class)
    public void unitMembers_twoRoots() throws Exception {
        Company company = new Company();
        Member employee1 = new Member("employee1");
        Member supervisor1 = new Member("supervisor1");
        Member employee2 = new Member("employee2");
        Member supervisor2 = new Member("supervisor2");
        company.unitMembers(employee1, supervisor1);
        company.unitMembers(employee2, supervisor2);

        // assert
        company.getRoot();
    }

    @Test(expected = ServiceException.class)
    public void unitMembers_twoSupervisors() throws Exception {
        Company company = new Company();
        Member employee = new Member("employee");
        Member supervisor1 = new Member("supervisor1");
        Member supervisor2 = new Member("supervisor2");
        company.unitMembers(employee, supervisor1);
        company.unitMembers(employee, supervisor2);
    }
}