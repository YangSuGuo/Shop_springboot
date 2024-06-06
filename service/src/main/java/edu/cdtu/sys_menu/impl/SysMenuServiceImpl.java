package edu.cdtu.sys_menu.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.entity.sys_menu.MakeMenuTree;
import edu.cdtu.entity.sys_menu.SysMenu;
import edu.cdtu.mapper.SysMenuMapper;
import edu.cdtu.sys_menu.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    // 获取父级菜单
    @Override
    public List<SysMenu> getParent() {
        //查询菜单
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().eq(SysMenu::getType, "1");
        List<SysMenu> menuList = this.baseMapper.selectList(query);
        //构造顶级
        SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("一级菜单");
        menu.setLabel("一级菜单");
        menu.setValue(0L);
        menuList.add(menu);
        //组装树数据
        return MakeMenuTree.makeTree(menuList, -1L);
    }
}
