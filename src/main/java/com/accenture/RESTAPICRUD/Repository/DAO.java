package com.accenture.RESTAPICRUD.Repository;

import com.accenture.RESTAPICRUD.Domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAO extends JpaRepository<Employee, Long> {


}
