package edu.cdtu.entity.user;

import lombok.Data;

@Data
public class UpdatePasswordParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
