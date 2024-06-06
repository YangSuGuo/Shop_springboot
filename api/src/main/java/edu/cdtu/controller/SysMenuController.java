package edu.cdtu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.cdtu.entity.ResultVo;
import edu.cdtu.entity.sys_menu.MakeMenuTree;
import edu.cdtu.entity.sys_menu.SysMenu;
import edu.cdtu.sys_menu.SysMenuService;
import edu.cdtu.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody SysMenu sysMenu) {
        sysMenu.setCreateTime(new Date());
        if (sysMenuService.save(sysMenu)) {
            return ResultUtils.success("新增成功!");
        }
        return ResultUtils.error("新增失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysMenu sysMenu) {
        if (sysMenuService.updateById(sysMenu)) {
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{menuId}")
    public ResultVo delete(@PathVariable("menuId") Long menuId) {
        if (sysMenuService.removeById(menuId)) {
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list() {
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList = sysMenuService.list(query);
        //组装树形数据
        List<SysMenu> list = MakeMenuTree.makeTree(menuList, 0L);
        return ResultUtils.success("查询成功", list);
    }

    //上级菜单
    @GetMapping("/getParent")
    public ResultVo getParent() {
        List<SysMenu> parent = sysMenuService.getParent();
        return ResultUtils.success("查询成功", parent);
    }
}
