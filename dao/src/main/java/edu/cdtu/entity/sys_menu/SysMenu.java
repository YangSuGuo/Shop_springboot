package edu.cdtu.entity.sys_menu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统资源，需要有权限控制的资源，如菜单项、按钮等
 */
@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private Long parentId;// 父级菜单的menuId，如果没有父级菜单，则parentId为0
    private String title;// 菜单名，即"首页"、"系统管理"、"闲置商品"等
    private String code; // 某个资源或功能的唯一标识(建议带上语义)，如sys:adminUser表示"管理员管理"菜单，sys:adminUser:delete表示"管理员管理页面"中的"删除"按钮
    @TableField(exist = false)
    private Long value; // 为了前端组件使用的字段，值就是menuId的值
    @TableField(exist = false)
    private String label; // 为了前端组件使用的字段，值就是title的值，即"首页"、"系统管理"、"闲置商品"等
    private String type; // 标识资源的类型，1为菜单项，2为按钮
    private String icon; // 如果资源类型为菜单项，icon记录其显示时使用的前端组件的ElementPlus图标组件名，如Setting
    private String path; // 如果资源类型为菜单项，记录其相应的路由跳转的路由地址
    private String parentName; // 父级菜单名的冗余记录，方便使用时直接展示，也可以不存，因为已经记录了parentId了
    private Integer orderNum; // 每个子资源在其父资源下的排序编号
    private Date createTime; // 资源的创建时间
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>(); // 在返回给前端时，将当前资源的子资源组装好返回

    public SysMenu() {
    }

    public SysMenu(long l, long l1, String root) {
        this.menuId = l;
        this.parentId = l1;
        this.title = root;
    }
}
