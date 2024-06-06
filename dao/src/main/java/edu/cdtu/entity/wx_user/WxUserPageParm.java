package edu.cdtu.entity.wx_user;

import lombok.Data;

@Data
public class WxUserPageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
}
