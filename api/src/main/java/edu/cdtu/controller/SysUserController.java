package edu.cdtu.controller;

// 已废弃
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//转用
// import java.util.Base64;
//import java.util.Base64.Encoder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.user.*;
import edu.cdtu.entity.wx_user.LoginVo;
import edu.cdtu.user.SysUserService;
import edu.cdtu.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Resource
    StringRedisTemplate redis;

    @GetMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) {
        // 生成验证码字符
        String text = defaultKaptcha.createText();
        // todo 设置生成唯一键值【毫秒】
        long zone = ZonedDateTime.now().withNano(0).toInstant().toEpochMilli();
        String key = DigestUtils.md5DigestAsHex(String.valueOf(zone).getBytes());
        // 验证码保存到 redis 设置过期时间120秒
        redis.opsForValue().set("code" + key, text, 120, TimeUnit.SECONDS);
        // 生成的验证码图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            // 将bufferedImage对象转换为JPEG格式的图片数据，然后写入到outputStream中。
            ImageIO.write(bufferedImage, "jpg", outputStream);
            // 创建一个BASE64Encoder对象，用于将图片数据转换为Base64字符串
            Encoder encoder = Base64.getEncoder();
            // 将outputStream中的图片数据转换为Base64字符串
            String base64 = encoder.encodeToString(outputStream.toByteArray());
            // 将生成的Base64字符串添加"data:image/jpeg;base64,"前缀，形成完整的Base64图片字符串
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            // 将生成的Base64图片字符串作为数据返回
            CodeKey vo = new CodeKey();
            vo.setKey(key);
            vo.setCaptchaBase64(captchaBase64);
            ResultVo result = new ResultVo("生成成功", 200, vo);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request) {
        String keyParm = parm.getUserId();
        // 获取redis里面的code验证码
        String code = redis.opsForValue().get("code" + keyParm);
        // 获取前端传来的验证码
        String codeParm = parm.getCode();
        System.out.println("前端请求验证码：" + codeParm);
        System.out.println("后端验证验证码：" + code);
        if (StringUtils.isEmpty(code)) return ResultUtils.error("验证码过期!");
        // 对比验证码
        if (!codeParm.equals(code)) return ResultUtils.error("验证码错误！");
        // 验证用户信息
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, parm.getUsername()).eq(SysUser::getPassword, DigestUtils.md5DigestAsHex(parm.getPassword().getBytes()));
        SysUser user = sysUserService.getOne(query);
        if (user == null) return ResultUtils.error("用户名或密码错误！");
        if (user.getStatus().equals("1")) return ResultUtils.error("账户被停用，请联系管理员！");
        // 返回登录信息
        LoginVo vo = new LoginVo();
        vo.setUserId(user.getUserId());
        vo.setNickName(user.getNickName());
        // 登录成功 使redis code码失效
        redis.delete("code" + keyParm);
        return ResultUtils.success("登录成功", vo);
    }

    @PutMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody UpdatePasswordParm parm) {
        // 验证原密码是否正确
        SysUser user = sysUserService.getById(parm.getUserId());
        String oldPassword = DigestUtils.md5DigestAsHex(parm.getOldPassword().getBytes());
        if (!user.getPassword().equals(oldPassword)) {
            return ResultUtils.error("原密码不正确！");
        }
        // 设置新密码
        UpdateWrapper<SysUser> query = new UpdateWrapper<>();
        query.lambda().set(SysUser::getPassword, DigestUtils.md5DigestAsHex(parm.getPassword().getBytes())).eq(SysUser::getUserId, parm.getUserId());
        if (sysUserService.update(query)) {
            return ResultUtils.success("修改成功！");
        }
        return ResultUtils.error("修改失败！");
    }

    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser) {
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        if (sysUserService.save(sysUser)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //修改
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser) {
        if (sysUserService.updateById(sysUser)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
        if (sysUserService.removeById(userId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //查询
    @GetMapping("/getList")
    public ResultVo getList(PageParm parm) {
        /*LambdaQueryWrapper<SysUser> q2 = new LambdaQueryWrapper<>();
        q2.like(StringUtils.isNotEmpty(parm.getNickName()),SysUser::getNickName,parm.getNickName());*/
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().like(StringUtils.isNotEmpty(parm.getNickName()), SysUser::getNickName, parm.getNickName());
        //构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //查询
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功", list);
    }

    @GetMapping("/list")
    public ResultVo getList(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam(required = false) String nickName) {
        MyPage<SysUser> page = sysUserService.getByPages(currentPage, pageSize, nickName);
        return ResultUtils.success("查询成功", page);
    }

}
