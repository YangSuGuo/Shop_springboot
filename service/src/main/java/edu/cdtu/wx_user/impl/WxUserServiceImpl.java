package edu.cdtu.wx_user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.wx_user.WxUser;
import edu.cdtu.mapper.WxUserMapper;
import edu.cdtu.wx_user.WxUserService;
import org.springframework.stereotype.Service;

@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {
}
