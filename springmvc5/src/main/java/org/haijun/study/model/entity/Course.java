package org.haijun.study.model.entity;

import lombok.Data;
import org.haijun.study.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 课程
 */
@Entity
@Data
// 可以指定schema{MySQL=数据库名 ，Oracle=用户 ID，SQLServer=对象属主名，DB2=Catalog 属主名，Sybase=数据库属主名}
// 仅在允许自动更新数据库表结构的场景中起到作用
@Table(name = "t_course" ,indexes={@Index(name="idx_course_name",columnList = "name")})//课程.添加索引，对各用，号隔开。可以配置多个
public class Course  extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)// mysql 自增列
    private Long id;

    @Column(length=20)
    private String name; //部门名称

    //多对多关系的管理方的定义取决于JoinTable的定义方，至于由哪一边作为管理方，取决于业务的关系，所以并不固定。
    // 如果添加数据时，应先添加管理关联方的对应的数据，在添加管理方对象，因为管理方有外键依赖对方
    // @JoinTable该标记与@Table注释类似，用于标注用于关联的表。可以标注在方法或者属性上
    //  name属性为连接两个表的表名称。若不指定，则使用默认“表名1”+“_”+“表名2”
    // joinColumns属性表示，在保存关系中的表中，所保存关联关系的外键的字段。并配合@JoinColumn标记使用。
    // 以下的映射配置，表示字段customer_id为外键关联到customer表中的id字段。
    // https://blog.csdn.net/GrayWoft/article/details/8853191
    @ManyToMany(targetEntity = User.class ,cascade={CascadeType.ALL},  fetch = FetchType.LAZY) // 一般在多个一方管理另外方的级联关系
    // 配置中间表信息
    @JoinTable(name = "t_course_user", joinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "id"), // , columnDefinition="comment '用户id'"
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private List<User> users;
}
