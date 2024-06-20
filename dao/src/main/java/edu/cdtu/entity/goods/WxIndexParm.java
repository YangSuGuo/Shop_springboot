package edu.cdtu.entity.goods;

import lombok.Data;

//首页参数
@Data
public class WxIndexParm {
    private Long currentPage;
    private Long pageSize;
    private String keywords;//搜索关键字
    private String categoryId;//分类id
}

