package edu.cdtu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.cdtu.entity.user.SysUser;
import org.springframework.stereotype.Component;

public class sys_user {
    @Component
    public interface SysUserMapper extends BaseMapper<SysUser> {

    }
}