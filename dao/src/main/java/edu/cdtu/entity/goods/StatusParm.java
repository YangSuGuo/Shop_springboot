package edu.cdtu.entity.goods;

import lombok.Data;

@Data
public class StatusParm {
    private Long goodsId;
    private String status;
    private String setIndex;
}
