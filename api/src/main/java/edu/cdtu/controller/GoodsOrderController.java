package edu.cdtu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.entity.goods.MyGoodsParm;
import edu.cdtu.entity.goods_order.GoodsOrder;
import edu.cdtu.entity.goods_order.OrderParm;
import edu.cdtu.entity.goods_order.OrderVo;
import edu.cdtu.goods_order.GoodsOrderService;
import edu.cdtu.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/goodsOrder")
public class GoodsOrderController {
    @Autowired
    private GoodsOrderService goodsOrderService;

    //交易订单
    @PostMapping("/replaceOrder")
    public ResultVo replaceOrder(@RequestBody GoodsOrder order) {
        goodsOrderService.replaceOrder(order);
        return ResultUtils.success("交易成功!");
    }

    //查看我的购买订单
    @GetMapping("/getMyOrder")
    public ResultVo getMyOrder(MyGoodsParm parm) {
        IPage<Goods> list = goodsOrderService.getMyOrder(parm);
        return ResultUtils.success("查询成功！", list);
    }

    //我的出售订单
    @GetMapping("/getSellOrder")
    public ResultVo getSellOrder(MyGoodsParm parm) {
        IPage<Goods> list = goodsOrderService.getSellOrder(parm);
        return ResultUtils.success("查询成功！", list);
    }

    //删除订单
    @PostMapping("/deleteOrder")
    public ResultVo deleteOrder(@RequestBody GoodsOrder parm) {
        if (goodsOrderService.removeById(parm.getOrderId())) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //查询闲置订单列表
//    @Auth
    @GetMapping("/getUnusedOrderList")
    public ResultVo getUnusedOrderList(OrderParm parm) {
        IPage<OrderVo> list = goodsOrderService.getUnusedOrderList(parm);
        return ResultUtils.success("查询成功", list);
    }

    //查询购买订单列表
    @GetMapping("/getBuyOrderList")
    public ResultVo getBuyOrderList(OrderParm parm) {
        IPage<OrderVo> list = goodsOrderService.getBuyOrderList(parm);
        return ResultUtils.success("查询成功", list);
    }
}
