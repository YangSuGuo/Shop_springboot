package edu.cdtu.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.user.MyPage;
import edu.cdtu.entity.user.SysUser;
import edu.cdtu.mapper.SysUserMapper;
import edu.cdtu.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public MyPage<SysUser> getByPages(int currentPage, int pageSize, String nickname) {
        int offset = (currentPage - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("size", pageSize);
        params.put("nickname", nickname);

        List<SysUser> userList = sysUserMapper.selectUserPage(params);

        // 计算总记录数（通常需要一个额外的查询）
        int totalCount = sysUserMapper.selectUserCount(nickname); // 假设有这个方法返回总记录数

        // 封装分页信息
        MyPage<SysUser> myPage = new MyPage<>(currentPage, pageSize, totalCount);
        myPage.setRecords(userList);

        return myPage;
    }
}
