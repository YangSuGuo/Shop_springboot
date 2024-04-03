package edu.cdtu.controller;

import edu.cdtu.SysUserService;
import edu.cdtu.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sysusers")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    @GetMapping("/all")
    public List<SysUser> getAllUsers() {
        return sysUserService.getAllUsers();
    }

}
