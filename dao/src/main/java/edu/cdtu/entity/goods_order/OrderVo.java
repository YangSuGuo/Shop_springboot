package edu.cdtu.entity.goods_order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo {
    private Long orderId;
    private String goodsName;
    private String image;
    private String nickName;
    private BigDecimal goodsPrice;
    private BigDecimal price;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
