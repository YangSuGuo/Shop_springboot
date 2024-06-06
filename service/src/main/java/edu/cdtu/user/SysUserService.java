package edu.cdtu.user;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.entity.user.MyPage;
import edu.cdtu.entity.user.SysUser;

public interface SysUserService extends IService<SysUser> {
    MyPage<SysUser> getByPages(int currentPage, int pageSize, String nickName);
}
