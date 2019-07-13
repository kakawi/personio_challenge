package com.hlebon.personio_challenge.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Company {
    private Collection<Member> allMembers = new LinkedList<>();

    public void unitMembers(Member employee, Member supervisor) throws ServiceException {
        if (employee == null || supervisor == null) {
            throw new ServiceException("Employee and Supervisor can't be null");
        }
        Member existedEmployee = null;
        Member existedSupervisor = null;
        for (Member currentMember : allMembers) {
            if (employee.equals(currentMember)) {
                existedEmployee = currentMember;
            }
            if (supervisor.equals(currentMember)) {
                existedSupervisor = currentMember;
            }
        }
        if (existedEmployee == null) {
            allMembers.add(employee);
        }
        if (existedSupervisor == null) {
            allMembers.add(supervisor);
        }

        if (existedEmployee != null) {
            if (existedEmployee.getSupervisor() != null) {
                String message = "Can't add supervisor %s to %s, because it already has %s as supervisor";
                throw new ServiceException(String.format(
                        message,
                        employee.getName(),
                        supervisor.getName(),
                        employee.getSupervisor().getName()
                ));
            }
            if (existedSupervisor != null) {
                // loop checking
                loopValidation(existedEmployee, existedSupervisor);
                existedEmployee.setSupervisor(existedSupervisor);
                return;
            }
            existedEmployee.setSupervisor(supervisor);
            return;
        }
        // everything OK
        if (existedSupervisor == null) {
            employee.setSupervisor(supervisor);
            return;
        }
        employee.setSupervisor(existedSupervisor);
    }

    private void loopValidation(Member existedEmployee, Member existedSupervisor) throws ServiceException {
        Collection<Member> supervisors = new LinkedList<>();
        while (existedSupervisor.getSupervisor() != null) {
            Member supervisorOfSupervisor = existedSupervisor.getSupervisor();
            supervisors.add(supervisorOfSupervisor);
            if (supervisorOfSupervisor.equals(existedEmployee)) {
                supervisors.add(existedEmployee);
                String loop = supervisors.stream().map(Member::getName).collect(Collectors.joining(" -> "));
                throw new ServiceException("Loop of supervisors " + loop);
            }
            existedSupervisor = supervisorOfSupervisor;
        }
    }

}
