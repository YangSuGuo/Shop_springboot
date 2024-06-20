package edu.cdtu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.wx_user.*;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.wx_user.WxUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wxUser")
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    @PostMapping("/register")
    public ResultVo register(@RequestBody WxUser user) {
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.lambda().eq(WxUser::getUsername, user.getUsername());
        WxUser one = wxUserService.getOne(query);
        if (one != null) {
            return ResultUtils.error("用户名被占用");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        if (wxUserService.saveOrUpdate(user)) {
            return ResultUtils.success("注册成功！");
        }
        return ResultUtils.error("注册失败！");
    }

    @PostMapping("/login")
    public ResultVo login(@RequestBody WxUser user) {
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.lambda().eq(WxUser::getUsername, user.getUsername()).
                eq(WxUser::getPassword, DigestUtils.md5DigestAsHex(user.getPassword().getBytes())).
                ne(WxUser::getDeleteStatus, "1");
        WxUser wxUser = wxUserService.getOne(query);
        if (wxUser != null) {
            if (wxUser.getStatus().equals("1")) {
                return ResultUtils.error("您的账户被停用，请联系管理员！");
            }
            LoginVo vo = new LoginVo();
            vo.setNickName(wxUser.getNickName());
            vo.setPhone(wxUser.getPhone());
            vo.setUserId(wxUser.getUserId());
            return ResultUtils.success("登陆成功", vo);
        }
        return ResultUtils.error("用户名或密码错误！");
    }

    @GetMapping("/list")
    public ResultVo getList(WxUserPageParm parm) {
        IPage<WxUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getPhone()), WxUser::getPhone, parm.getPhone())
                .ne(WxUser::getDeleteStatus, "1").orderByDesc(WxUser::getUsername);
        IPage<WxUser> list = wxUserService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    // 停用/启用
    @PostMapping("/stopUser")
    public ResultVo stopUser(@RequestBody WxUser user) {
        if (!"0".equals(user.getStatus()) && !"1".equals(user.getStatus())) {
            return ResultUtils.error("无效状态！");
        }
        UpdateWrapper<WxUser> query = new UpdateWrapper<>();
        query.lambda().set(WxUser::getStatus, user.getStatus()).eq(WxUser::getUserId, user.getUserId());
        if (wxUserService.update(query)) {
            return ResultUtils.success("设置成功！");
        }
        return ResultUtils.error("设置失败！");
    }

    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody WxUser user) {
        String pass = "123456";
        UpdateWrapper<WxUser> query = new UpdateWrapper<>();
        query.lambda().set(WxUser::getPassword, DigestUtils.md5DigestAsHex(pass.getBytes())).eq(WxUser::getUserId, user.getUserId());
        if (wxUserService.update(query)) {
            return ResultUtils.success("重置成功！");
        }
        return ResultUtils.success("重置失败！");
    }

    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
//        if (wxUserService.removeById(userId)) {
//            return ResultUtils.success("删除成功！");
//        }
//        return ResultUtils.error("删除失败！");
        UpdateWrapper<WxUser> query = new UpdateWrapper<>();
        query.lambda().set(WxUser::getDeleteStatus, "1").eq(WxUser::getUserId, userId);
        if (wxUserService.update(query)) {
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    //小程序更新密码
    @PostMapping("/wxupdatePassword")
    public ResultVo wxupdatePassword(@RequestBody UpdateParm parm) {
        //判断原密码是否正确
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.lambda().eq(WxUser::getUserId, parm.getUserId())
                .eq(WxUser::getPassword, DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes()));
        WxUser one = wxUserService.getOne(query);
        if (one == null) {
            return ResultUtils.error("原密码不正确!");
        }
//        修改密码
        UpdateWrapper<WxUser> update = new UpdateWrapper<>();
        update.lambda().set(WxUser::getPassword, DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()))
                .eq(WxUser::getUserId, parm.getUserId());
        if (wxUserService.update(update)) {
            return ResultUtils.success("密码修改成功!");
        }
        return ResultUtils.error("密码修改失败！");
    }

    //小程序修改用户个人信息
    @PostMapping("/editInfo")
    public ResultVo editInfo(@RequestBody WxUser wxUser) {
        if (wxUserService.updateById(wxUser)) {
            return ResultUtils.success("修改成功!");
        }
        return ResultUtils.error("修改失败!");
    }

    //小程序查询用户个人信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(Long userId) {
        WxUser user = wxUserService.getById(userId);
        return ResultUtils.success("查询成功!", user);
    }

    //忘记密码
    @PostMapping("/forget")
    public ResultVo forget(@RequestBody ForgetParm parm) {
        //查询用户是否存在
        QueryWrapper<WxUser> query = new QueryWrapper<>();
        query.lambda().eq(WxUser::getUsername, parm.getUsername())
                .eq(WxUser::getPhone, parm.getPhone());
        WxUser one = wxUserService.getOne(query);
        if (one == null) {
            return ResultUtils.error("账户或电话号码不正确!");
        }
        //更新条件
        UpdateWrapper<WxUser> update = new UpdateWrapper<>();
        update.lambda().set(WxUser::getPassword, DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()))
                .eq(WxUser::getUsername, parm.getUsername())
                .eq(WxUser::getPhone, parm.getPhone());
        if (wxUserService.update(update)) {
            return ResultUtils.success("重置成功!");
        }
        return ResultUtils.error("修改失败!");
    }
}
