package org.haijun.study.TreeMenu;

import java.util.*;

public class TreeExcutTools {

    /*
    菜单一般需要排序，我们根据Menu的order字段进行排序：
     * 排序,根据order排序
     */
    public Comparator<Menu> order(){
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getOrder() != o2.getOrder()){
                    return o1.getOrder() - o2.getOrder();
                }
                return 0;
            }
        };
        return comparator;
    }

    /**
     * 生成树的方法
     * @return
     */
    public Map<String,Object> findTree(){
        Map<String,Object> data = new HashMap<>();
        try {//查询所有菜单
            //List<Menu> allMenu = menuDao.findTree();
            List<Menu> allMenu = new ArrayList<>();
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParentId().equals("0")){//父节点是0的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            data.put("success", "true");
            data.put("list", rootMenu);
            return data;
        } catch (Exception e) {
            data.put("success", "false");
            data.put("list", new ArrayList());
            return data;
        }
    }

    /**
     * 获取子节点
     * @param id 父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Menu> getChild(String id,List<Menu> allMenu){
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }
}
