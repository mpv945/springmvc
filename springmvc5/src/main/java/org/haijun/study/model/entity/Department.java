package org.haijun.study.model.entity;

import lombok.Data;
import org.haijun.study.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 部门表，一个部门有多个员工
 */
@Entity
@Data
@Table(name = "t_department")//部门
public class Department  extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)// mysql 自增列
    private Long id;

    @Column(length=20)
    private String name; //部门名称

    // 赖加载多的一方数据
    @OneToMany(targetEntity=User.class, mappedBy="department", fetch=FetchType.LAZY)// mappedBy把自己一方维护关系交出去，维护关系交给多的一方
    @OrderBy("name ASC")//排序
    private List<User> users;//用户列表
}
