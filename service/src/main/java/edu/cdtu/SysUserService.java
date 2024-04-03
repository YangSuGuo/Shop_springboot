package edu.cdtu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.cdtu.entity.user.SysUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysUserService {

    /**
     * 获取所有用户列表
     *
     * @return 用户列表
     */
    List<SysUser> getAllUsers();

}
