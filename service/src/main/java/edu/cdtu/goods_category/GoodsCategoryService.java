package edu.cdtu.goods_category;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.entity.goods_category.GoodsCategory;

public interface GoodsCategoryService extends IService<GoodsCategory> {
    boolean saveGoodsCategory(GoodsCategory goodsCategory);
}
