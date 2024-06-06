package edu.cdtu.goods_category.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.goods_category.GoodsCategory;
import edu.cdtu.goods_category.GoodsCategoryService;
import edu.cdtu.mapper.GoodsCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public boolean saveGoodsCategory(GoodsCategory goodsCategory) {
        QueryWrapper<GoodsCategory> query = new QueryWrapper<>();
        query.lambda().eq(GoodsCategory::getOrderNum, goodsCategory.getOrderNum());
        int count = goodsCategoryMapper.selectCount(query);
        if (count > 0) {
            return false;
        }
        return goodsCategoryMapper.insert(goodsCategory) > 0;
    }
}
