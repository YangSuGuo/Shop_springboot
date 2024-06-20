package edu.cdtu.entity.wx_user;

import lombok.Data;

@Data
public class ForgetParm {
    private String phone;
    private String username;
    private String password;
}
