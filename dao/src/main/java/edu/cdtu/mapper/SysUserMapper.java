package edu.cdtu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.entity.user.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
    // 分页查询用户列表
    /*List<SysUser> selectUserPage(Map<String, Object> params);
    // 统计用户总数
    int selectUserCount(String nickname);*/
}
