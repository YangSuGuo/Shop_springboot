package edu.cdtu.goods_order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.entity.goods.MyGoodsParm;
import edu.cdtu.entity.goods_order.GoodsOrder;
import edu.cdtu.entity.goods_order.OrderParm;
import edu.cdtu.entity.goods_order.OrderVo;

import java.math.BigDecimal;

public interface GoodsOrderService extends IService<GoodsOrder> {
    //    交易订单
    void replaceOrder(GoodsOrder goodsOrder);

    //查询我的订单
    IPage<Goods> getMyOrder(MyGoodsParm parm);

    //查询出售订单
    IPage<Goods> getSellOrder(MyGoodsParm parm);

    //查询闲置订单列表
    IPage<OrderVo> getUnusedOrderList(OrderParm parm);

    //查询闲置订单列表数量
    Long getQueryIdleOrderListCount();

    //查询求购订单列表
    IPage<OrderVo> getBuyOrderList(OrderParm parm);

    //查询求购订单列表数量
    Long getQueryPurchaseOrderListCount();

    //查询订单总额
    BigDecimal getQueryOrderTotalAmount();
}
