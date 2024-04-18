package edu.cdtu.goods_category.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.goods_category.GoodsCategory;
import edu.cdtu.goods_category.GoodsCategoryService;
import edu.cdtu.mapper.GoodsCategoryMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {
}
