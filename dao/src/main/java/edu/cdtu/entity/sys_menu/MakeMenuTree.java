package edu.cdtu.entity.sys_menu;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeMenuTree {
    /**
     * 从menuList中找到parentId为pid的SysMenu返回
     * @param menuList 待整理成树形结构的menu列表，即目前是数据库中原始的平铺的menu列表结构
     * @param pid 指定的parentId，用于筛选出该parentId下的直属children SysMenu
     * @return 返回parentId为pid的所有children SysMenu
     */
//    树状结构
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid) {
        //接收组装后的树数据
        List<SysMenu> list = new ArrayList<>();
        //判断menuList是否为空，如果为空，直接返回空的数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    //组装树数据
                    SysMenu menu = new SysMenu();
                    //快速复制值
                    BeanUtils.copyProperties(item, menu);
                    //设置
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getMenuId());
                    //递归查找下级,自己调用自己
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
