package com.BackendProject.Shopeeee.request;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String LastName;

}
