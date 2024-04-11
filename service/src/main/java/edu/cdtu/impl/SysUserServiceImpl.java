package edu.cdtu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.SysUserService;
import edu.cdtu.entity.user.SysUser;
import edu.cdtu.mapper.sys_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysUserServiceImpl extends ServiceImpl<sys_user.SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private sys_user.SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> getAllUsers() {
        return sysUserMapper.selectList(null);
    }

}
