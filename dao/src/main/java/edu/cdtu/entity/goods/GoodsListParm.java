package edu.cdtu.entity.goods;

import lombok.Data;

@Data
public class GoodsListParm {
    private Long currentPage;
    private Long pageSize;
    private String goodsName;
}
