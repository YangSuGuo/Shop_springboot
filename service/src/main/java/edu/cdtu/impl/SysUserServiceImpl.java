package edu.cdtu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.SysUserService;
import edu.cdtu.entity.user.SysUser;
import edu.cdtu.mapper.sys_user;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<sys_user.SysUserMapper, SysUser> implements SysUserService { }
