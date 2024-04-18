package edu.cdtu.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.user.SysUser;
import edu.cdtu.mapper.SysUserMapper;
import edu.cdtu.user.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService { }
