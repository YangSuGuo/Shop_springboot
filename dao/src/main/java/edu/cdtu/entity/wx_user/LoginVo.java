package edu.cdtu.entity.wx_user;

import lombok.Data;

/**
 * 登录成功返回的数据
 */
@Data
public class LoginVo {
    private Long userId;
    private String phone;
    private String nickName;
    private String token;
}
