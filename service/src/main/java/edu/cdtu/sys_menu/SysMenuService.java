package edu.cdtu.sys_menu;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.cdtu.entity.sys_menu.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    //    查询父级菜单
    List<SysMenu> getParent();
}
