package com.accenture.RESTAPICRUD.Repository;

import com.accenture.RESTAPICRUD.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DAO extends JpaRepository<Employee, Long> {


}
