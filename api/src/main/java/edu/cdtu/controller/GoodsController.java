package edu.cdtu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.goods.*;
import edu.cdtu.entity.goods_category.GoodsCategory;
import edu.cdtu.goods.GoodsService;
import edu.cdtu.goods_category.GoodsCategoryService;
import edu.cdtu.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @PostMapping("/release")
    public ResultVo release(@RequestBody Goods goods) {
        goods.setCreateTime(new Date());
        if (goodsService.save(goods)) {
            return ResultUtils.success("发布成功！");
        }
        return ResultUtils.error("发布失败！");
    }


    // 闲置商品
    @GetMapping("/list")
    public ResultVo getList(GoodsListParm parm) {
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda()
                .eq(Goods::getType, "0")
                .eq(Goods::getDeleteStatus, "0")
                .like(StringUtils.isNotEmpty(parm.getGoodsName()),
                        Goods::getGoodsName, parm.getGoodsName())
                .orderByDesc(Goods::getCreateTime);
        IPage<Goods> list = goodsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    // 查询闲置商品数量
    @GetMapping("/queryIdleItemCount")
    public ResultVo getQueryIdleItemCount() {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda()
                .eq(Goods::getType, "0")
                .eq(Goods::getDeleteStatus, "0")
                .orderByDesc(Goods::getCreateTime);
        Integer count = goodsService.count(query);
        return ResultUtils.success("查询成功", count);
    }

    // 查询求购商品数量
    @GetMapping("/queryPurchaseItemQuantity")
    public ResultVo getQueryPurchaseItemQuantity() {
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda()
                .eq(Goods::getType, "1")
                .eq(Goods::getDeleteStatus, "0")
                .orderByDesc(Goods::getCreateTime);
        Integer count = goodsService.count(query);
        return ResultUtils.success("查询成功", count);
    }


    // 上下架商品
    @PostMapping("/upanddown")
    public ResultVo upanddown(@RequestBody StatusParm parm) {
        if (!"0".equals(parm.getStatus()) && !"1".equals(parm.getStatus())) {
            return ResultUtils.error("无效状态！");
        }
        UpdateWrapper<Goods> query = new UpdateWrapper<>();
        query.lambda().set(Goods::getStatus, parm.getStatus()).eq(Goods::getGoodsId, parm.getGoodsId());
        if (goodsService.update(query)) {
            return ResultUtils.success("设置成功!");
        }
        return ResultUtils.error("设置失败!");
    }

    //推荐首页
    @PostMapping("/setIndex")
    public ResultVo setIndex(@RequestBody StatusParm parm) {
        if (!"0".equals(parm.getSetIndex()) && !"1".equals(parm.getSetIndex())) {
            return ResultUtils.error("无效推荐状态！");
        }
        UpdateWrapper<Goods> query = new UpdateWrapper<>();
        query.lambda().set(Goods::getSetIndex, parm.getSetIndex()).eq(Goods::getGoodsId, parm.getGoodsId());
        if (goodsService.update(query)) {
            return ResultUtils.success("设置成功！");
        }
        return ResultUtils.error("设置失败！");
    }

    //后台删除商品
    @PostMapping("/delete")
    public ResultVo delete(@RequestBody StatusParm parm) {
        //@DeleteMapping("{goodsId}")
        //public ResultVo delete(@PathVariable("goodsId") Long goodsId){
        UpdateWrapper<Goods> query = new UpdateWrapper<>();
        query.lambda().set(Goods::getDeleteStatus, "1").eq(Goods::getGoodsId, parm.getGoodsId());
        //query.lambda().set(Goods::getDeleteStatus, "1").eq(Goods::getGoodsId, goodsId);
        if (goodsService.update(query)) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败!");
    }

    // 求购商品
    @GetMapping("/wanted/list")
    public ResultVo getWantedList(GoodsListParm parm) {
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda().eq(Goods::getType, "1").eq(Goods::getDeleteStatus, "0").like(StringUtils.isNotEmpty(parm.getGoodsName()), Goods::getGoodsName, parm.getGoodsName()).orderByDesc(Goods::getCreateTime);
        IPage<Goods> list = goodsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //小程序查询商品分类
    @GetMapping("/getCateList")
    public ResultVo getCateList() {
        List<GoodsCategory> list = goodsCategoryService.list();
        return ResultUtils.success("查询成功", list);
    }

    //小程序闲置列表查询
    @GetMapping("/getUsedList")
    public ResultVo getUsedList(WxIndexParm parm) {
        //构造查询条件
        QueryWrapper<Goods> query = new QueryWrapper<>();
        //关键字模糊查询
        query.lambda().like(StringUtils.isNotEmpty(parm.getKeywords()), Goods::getGoodsName, parm.getKeywords())
                .eq(Goods::getStatus, "0")//上架
                .eq(Goods::getDeleteStatus, "0")//未删除
                .eq(Goods::getType, "0")//闲置
                .eq(Goods::getSellStatus, "0")//未出售
                //请求分类与查询分类相同
                .eq(StringUtils.isNotEmpty(parm.getCategoryId()), Goods::getCategoryId, parm.getCategoryId())
                .orderByDesc(Goods::getCreateTime);//时间倒续

        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        IPage<Goods> list = goodsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //小程序求购列表查询
    @GetMapping("/getBuyList")
    public ResultVo getBuyList(WxIndexParm parm) {
        //构造查询条件
        QueryWrapper<Goods> query = new QueryWrapper<>();

        query.lambda().like(StringUtils.isNotEmpty(parm.getKeywords()), Goods::getGoodsName, parm.getKeywords())
                .eq(Goods::getStatus, "0")
                .eq(Goods::getDeleteStatus, "0")
                .eq(Goods::getType, "1")
                .eq(Goods::getSellStatus, "0")
                .eq(StringUtils.isNotEmpty(parm.getCategoryId()), Goods::getCategoryId, parm.getCategoryId())
                .orderByDesc(Goods::getCreateTime);

        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        IPage<Goods> list = goodsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //小程序我发布的商品
    @GetMapping("/getMyGoodsList")
    public ResultVo getMyUnusedList(MyGoodsParm parm) {
        //构造查询条件
        QueryWrapper<Goods> query = new QueryWrapper<>();
        query.lambda().eq(Goods::getUserId, parm.getUserId())
                .eq(Goods::getType, parm.getType())//商品类型：0闲置 1求购
                .eq(Goods::getDeleteStatus, "0");//未删除
        //构造分页对象
        IPage<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        IPage<Goods> list = goodsService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    //小程序修改发布的商品信息
    @PostMapping("/edit")
    public ResultVo edit(@RequestBody Goods goods) {
        if (goodsService.updateById(goods)) {
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败!");
    }
}
