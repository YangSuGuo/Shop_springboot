package edu.cdtu.entity.user;

import lombok.Data;

// 分页参数实体类
@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String nickName;
}
