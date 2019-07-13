package com.hlebon.personio_challenge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class CompanyTest {

    @Test
    public void unitMembers_success() throws Exception {
        Company company = new Company();
        Member employee = new Member("employee");
        Member supervisor = new Member("supervisor");
        company.unitMembers(employee, supervisor);
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

    @Test
    public void unitMembers_noRoot() throws Exception {
        // TODO write a test
        throw new Exception("write a test");
    }

    @Test(expected = ServiceException.class)
    public void unitMembers_twoRoots() throws Exception {
        Company company = new Company();
        Member employee = new Member("employee");
        Member supervisor1 = new Member("supervisor1");
        Member supervisor2 = new Member("supervisor2");
        company.unitMembers(employee, supervisor1);
        company.unitMembers(employee, supervisor2);
    }
}