package edu.cdtu;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.entity.user.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<SysUser> getAllUsers();

}
