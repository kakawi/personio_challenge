package com.hlebon.personio_challenge.service;

import com.hlebon.personio_challenge.repository.MemberDao;
import com.hlebon.personio_challenge.repository.entity.MemberEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final MemberDao memberDao;

    public CompanyServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public Member getRoot(Collection<Pair<Member, Member>> pairs) throws ServiceException {
        Company company = new Company();
        for (Pair<Member, Member> pair : pairs) {
            company.unitMembers(pair.getLeft(), pair.getRight());
        }
        Member root = company.getRoot();
        memberDao.deleteAll();
        memberDao.clearConnections();
        saveAllMembers(company.getAllMembers());
        saveConnections(root);
        return root;
    }

    @Override
    public Member getHierarchyByName(String name) {
        MemberEntity currentEntity = memberDao.findByName(name);
        if (currentEntity.getSupervisor() == null) {
            return null;
        }
        MemberEntity firstSupervisor = currentEntity.getSupervisor();
        currentEntity = firstSupervisor;
        Member result = new Member(firstSupervisor.getName());
        Member currentMember = result;

        while (currentEntity.getSupervisor() != null) {
            MemberEntity supervisor = currentEntity.getSupervisor();
            Member newSupervisor = new Member(supervisor.getName());
            currentMember.setSupervisor(newSupervisor);
            currentEntity = supervisor;
            currentMember = newSupervisor;
        }

        return result;
    }

    private void saveAllMembers(Collection<Member> allMembers) {
        for (Member currentMember : allMembers) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setName(currentMember.getName());
            memberDao.save(memberEntity);
        }
    }

    private void saveConnections(Member supervisor) {
        Collection<Member> employees = supervisor.getEmployees();
        MemberEntity supervisorEntity = memberDao.findByName(supervisor.getName());
        for (Member employee : employees) {
            MemberEntity employeeEntity = memberDao.findByName(employee.getName());
            memberDao.saveConnection(employeeEntity, supervisorEntity);
            saveConnections(employee);
        }
    }
}
