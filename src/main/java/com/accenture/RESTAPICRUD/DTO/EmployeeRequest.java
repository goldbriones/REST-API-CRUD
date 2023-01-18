package com.accenture.RESTAPICRUD.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor(staticName = "build")
@Data
@NoArgsConstructor
public class EmployeeRequest {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")

    private String lastName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "role_name")
    private String roleName;

}
