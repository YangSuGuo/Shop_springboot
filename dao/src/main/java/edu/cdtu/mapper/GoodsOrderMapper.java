package edu.cdtu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.entity.goods_order.GoodsOrder;
import edu.cdtu.entity.goods_order.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {

    //查询我的购买订单
    IPage<Goods> getMyOrder(IPage<Goods> page, @Param("userId") Long userId);

    //查询我的出售订单
    IPage<Goods> getSellOrder(IPage<Goods> page, @Param("userId") Long userId);

    //获取所有闲置订单
    IPage<OrderVo> getUnusedOrderList(IPage<OrderVo> page, @Param("goodsName") String goodsName);

    //获取所有闲置订单数量
    Long getQueryIdleOrderListCount();

    // 获取全部求购订单
    IPage<OrderVo> getBuyOrderList(IPage<OrderVo> page, @Param("goodsName") String goodsName);

    //获取所有闲置订单数量
    Long getQueryPurchaseOrderListCount();

    //获取全部订单总金额
    BigDecimal getQueryOrderTotalAmount();
}
