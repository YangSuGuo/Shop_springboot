package edu.cdtu.entity.user;

import lombok.Data;

@Data
public class LoginParm {
    private String userId;
    private String username;
    private String password;
    private String code;
}
