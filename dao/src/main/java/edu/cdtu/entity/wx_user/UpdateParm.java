package edu.cdtu.entity.wx_user;

import lombok.Data;

@Data
public class UpdateParm {
    private Long userId;
    private String password;
    private String oldPassword;
}
