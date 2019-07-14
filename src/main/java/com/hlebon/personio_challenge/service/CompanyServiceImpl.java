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
        saveMembers(root, null);
        return root;
    }

    @Override
    public Member getHierarchyByName(String name) {
        MemberEntity targetMember = memberDao.findByName(name);
        if (targetMember.getSupervisor() == null) {
            return null;
        }

        MemberEntity currentSupervisorEntity = targetMember.getSupervisor();
        Member currentSupervisor = new Member(currentSupervisorEntity.getName());
        Member result = currentSupervisor;
        while (currentSupervisorEntity.getSupervisor() != null) {
            MemberEntity parentSupervisorEntity = currentSupervisorEntity.getSupervisor();
            Member parentSupervisor = new Member(parentSupervisorEntity.getName());
            currentSupervisor.setSupervisor(parentSupervisor);
            currentSupervisor = parentSupervisor;
            currentSupervisorEntity = parentSupervisorEntity;
        }
        return result;
    }

    private void saveMembers(Member employee, MemberEntity supervisor) {
        MemberEntity employeeEntity = new MemberEntity();
        employeeEntity.setName(employee.getName());
        employeeEntity.setSupervisor(supervisor);
        memberDao.save(employeeEntity);
        for (Member childEmployee : employee.getEmployees()) {
            saveMembers(childEmployee, employeeEntity);
        }
    }

}
