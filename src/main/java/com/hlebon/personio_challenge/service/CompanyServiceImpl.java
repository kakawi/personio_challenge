package com.hlebon.personio_challenge.service;

import com.hlebon.personio_challenge.repository.MemberDao;
import com.hlebon.personio_challenge.repository.entity.ConnectionEntity;
import com.hlebon.personio_challenge.repository.entity.MemberEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
    public List<Member> getHierarchyByName(String name) {
        List<Member> result = new LinkedList<>();
        ConnectionEntity currentConnection = memberDao.getConnectionByEmployeeName(name);
        while (currentConnection != null) {
            MemberEntity supervisorEntity = currentConnection.getSupervisor();
            Member supervisor = new Member(supervisorEntity.getName());
            result.add(supervisor);
            currentConnection = memberDao.getConnectionByEmployeeName(supervisorEntity.getName());

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
