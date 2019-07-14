package com.hlebon.personio_challenge.service;

import com.hlebon.personio_challenge.repository.Member2Dao;
import com.hlebon.personio_challenge.repository.MemberDao;
import com.hlebon.personio_challenge.repository.entity.Member2;
import com.hlebon.personio_challenge.repository.entity.MemberEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final MemberDao memberDao;
    private final Member2Dao member2Dao;

    public CompanyServiceImpl(MemberDao memberDao, Member2Dao member2Dao) {
        this.memberDao = memberDao;
        this.member2Dao = member2Dao;
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
        member2Dao.deleteAll();
        saveMembers(root, null);
        return root;
    }

    @Override
    public Member getHierarchyByName(String name) {
        Member2 targetMember = member2Dao.findByName(name);
        if (targetMember.getSupervisor() == null) {
            return null;
        }

        Member2 currentSupervisorEntity = targetMember.getSupervisor();
        Member currentSupervisor = new Member(currentSupervisorEntity.getName());
        Member result = currentSupervisor;
        while (currentSupervisorEntity.getSupervisor() != null) {
            Member2 parentSupervisorEntity = currentSupervisorEntity.getSupervisor();
            Member parentSupervisor = new Member(parentSupervisorEntity.getName());
            currentSupervisor.setSupervisor(parentSupervisor);
            currentSupervisor = parentSupervisor;
            currentSupervisorEntity = parentSupervisorEntity;
        }
        return result;
    }

    private void saveMembers(Member employee, Member2 supervisor) {
        Member2 employeeEntity = new Member2();
        employeeEntity.setName(employee.getName());
        employeeEntity.setSupervisor(supervisor);
        member2Dao.save(employeeEntity);
        for (Member childEmployee : employee.getEmployees()) {
            saveMembers(childEmployee, employeeEntity);
        }
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
