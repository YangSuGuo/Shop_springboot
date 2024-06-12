package edu.cdtu.entity.goods_order;

import lombok.Data;

@Data
public class OrderParm {
    private Long currentPage;
    private Long pageSize;
    private String goodsName;
}
