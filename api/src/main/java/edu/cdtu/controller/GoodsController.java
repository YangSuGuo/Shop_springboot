package edu.cdtu.controller;

import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.goods.GoodsService;
import edu.cdtu.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/release")
    public ResultVo release(@RequestBody Goods goods) {
        goods.setCreateTime(new Date());
        if (goodsService.save(goods)) {
            return ResultUtils.success("发布成功！");
        }
        return ResultUtils.error("发布失败！");
    }
}
