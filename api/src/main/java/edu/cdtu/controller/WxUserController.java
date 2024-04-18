package edu.cdtu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.wx_user.LoginVo;
import edu.cdtu.entity.wx_user.WxUser;
import edu.cdtu.utils.ResultUtils;
import edu.cdtu.wx_user.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        query.lambda().eq(WxUser::getUsername, user.getUsername()).eq(WxUser::getPassword, DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
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
}
