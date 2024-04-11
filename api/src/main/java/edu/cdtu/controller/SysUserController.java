package edu.cdtu.controller;

import edu.cdtu.SysUserService;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.user.SysUser;
import edu.cdtu.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @GetMapping("/all_users")
    public ResultVo<List<SysUser>> getAllUsers() {
        List<SysUser> users = sysUserService.getAllUsers();

        if (!users.isEmpty()) {
            return ResultUtils.success("查询成功", users);
        } else {
            return ResultUtils.error();
        }
    }
}
