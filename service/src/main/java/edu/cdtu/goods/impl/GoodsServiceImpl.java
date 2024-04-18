package edu.cdtu.goods.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.goods.Goods;
import edu.cdtu.goods.GoodsService;
import edu.cdtu.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
