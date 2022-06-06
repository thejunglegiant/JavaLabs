package edu.thejunglegiant.store.data.entity;

import edu.thejunglegiant.store.security.UserRole;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Arrays;

@Data
public class UserEntity {

    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String role;

    private Boolean isActive;

    public UserRole getUserRole() {
        return Arrays.stream(UserRole.values()).filter(item -> item.name().equals(role)).findFirst().orElse(UserRole.USER);
    }
}
