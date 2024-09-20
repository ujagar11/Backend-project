package com.BackendProject.Shopeeee.request;

import lombok.Data;
import org.hibernate.annotations.NaturalId;
@Data
public class CreateUserRequest {
    private String firstName;
    private String LastName;
    private String email;
    private String password;
}
