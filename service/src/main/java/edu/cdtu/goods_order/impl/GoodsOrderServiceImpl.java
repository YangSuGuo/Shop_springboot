package edu.cdtu.goods_order.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.entity.goods.MyGoodsParm;
import edu.cdtu.entity.goods_order.GoodsOrder;
import edu.cdtu.entity.goods_order.OrderParm;
import edu.cdtu.entity.goods_order.OrderVo;
import edu.cdtu.goods.GoodsService;
import edu.cdtu.goods_order.GoodsOrderService;
import edu.cdtu.mapper.GoodsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder>
        implements GoodsOrderService {

    @Autowired
    private GoodsService goodsService;

    @Override
    @Transactional
    public void replaceOrder(GoodsOrder goodsOrder) {
        //1、插入订单数据
        goodsOrder.setCreateTime(new Date());
        int insert = this.baseMapper.insert(goodsOrder);
        //2、更新商品状态
        if (insert > 0) {
            UpdateWrapper<Goods> query = new UpdateWrapper<>();
            query.lambda().set(Goods::getSellStatus, "1")//已出售
                    .set(Goods::getStatus, "1")//下架
                    .eq(Goods::getGoodsId, goodsOrder.getGoodsId());
            goodsService.update(query);
        }
    }

    //查询我的购买订单
    @Override
    public IPage<Goods> getMyOrder(MyGoodsParm parm) {
        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getMyOrder(page, parm.getUserId());
    }

    //    查询我的售出订单
    @Override
    public IPage<Goods> getSellOrder(MyGoodsParm parm) {
        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getSellOrder(page, parm.getUserId());
    }

    //    查询闲置订单列表
    @Override
    public IPage<OrderVo> getUnusedOrderList(OrderParm parm) {
        //构造分页对象
        IPage<OrderVo> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getUnusedOrderList(page, parm.getGoodsName());
    }

    //查询闲置订单列表数量
    @Override
    public Long getQueryIdleOrderListCount() {
        return this.baseMapper.getQueryIdleOrderListCount();
    }

    // 查询求购订单列表
    @Override
    public IPage<OrderVo> getBuyOrderList(OrderParm parm) {
        //构造分页对象
        IPage<OrderVo> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getBuyOrderList(page, parm.getGoodsName());
    }

    //查询求购订单列表数量
    @Override
    public Long getQueryPurchaseOrderListCount() {
        return this.baseMapper.getQueryPurchaseOrderListCount();
    }

    @Override
    public BigDecimal getQueryOrderTotalAmount() {
        return this.baseMapper.getQueryOrderTotalAmount();
    }
}
