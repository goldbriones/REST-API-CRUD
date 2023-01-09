package com.accenture.RESTAPICRUD.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_db")
@AllArgsConstructor(staticName = "build")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)

    private String lastName;

    @Column(name = "email_id" , nullable = false)
    private String emailId;

    @Column(name = "role_name", nullable = false)
    private String roleName;


    public Employee(String firstName, String lastName, String emailId, String roleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.roleName = roleName;
    }
}


