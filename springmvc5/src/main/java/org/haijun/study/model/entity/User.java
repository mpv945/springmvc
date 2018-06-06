package org.haijun.study.model.entity;

import lombok.Data;
import org.haijun.study.model.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

// 参考https://www.w3cschool.cn/java/jpa-elementcollection-mapkeyentity.html
@Entity
@Table(name = "t_user",uniqueConstraints = {@UniqueConstraint(columnNames = {"name","password"})}) //可以添加唯一约束，schema ，索引，联合
@Data
//@EntityListeners(AuditingEntityListener.class)// 添加审计监听，可以定义审计字段，例如创建人，早BaseEntiy
//@NamedQuery(name="User.findByNames",query = "select u from #{#entityName} u where u.name IN ?1")
//@Proxy(lazy=false) // 不启动赖加载，默认true；在缺省情况下，即时加载属性的值而延迟加载集合
// https://blog.csdn.net/petercnmei/article/details/54809257
public class User extends BaseEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)// mysql 自增列
    private Long id;

    /**
     * 姓名
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false,name = "password")
    private String pwd;

    // 如果单向一对一，直接定义一个 @JoinColumn(name="PSPACE_ID")，本地PSPACE_ID字段关联到关系表id（不指定为关系表主键）
    @OneToOne(fetch = FetchType.LAZY) //用户对身份证赖加载，查询用户信息不需要立即需要身份证信息
    @JoinColumn(name="card_id")
    /**
     * 身份证信息
     */
    private IdentityCard identityCard;

    /**
     * 多个用户属于同一个部门，
     */
    // CascadeType.MERGE:级联合并（级联更新）；CascadeType.REFRESH:级联刷新：获取order对象里也同时也重新获取最新的items时的对象。
    // CascadeType.PERSIST：级联新增（又称级联保存）；CascadeType.REMOVE:级联删除
    @ManyToOne(cascade={CascadeType.ALL})//级联操作，如果新增一个用户，bean包含部门信息，而该部门信息不存在，则级联新增部门信息
    @JoinColumn(name="department_id" )
    // 多列连接
/*    @JoinColumns({
            @JoinColumn(name="DEPT_NAME", referencedColumnName="Name"),// referencedColumnName 部门表的列名
            @JoinColumn(name="DEPT_ID", referencedColumnName="ID")
    })*/
    private Department department;

    /**
     * 用户学习的课程信息
     */
    @ManyToMany(targetEntity = Course.class , mappedBy = "users" , fetch = FetchType.LAZY)// 用课程去管理
    private List<Course> courseList;


    /**
     * 年龄
     */
    @Column(length = 3)
    private long age;

    /**
     * 生日
     */
    private Date birthday;

    @Transient
    private String convertedName; //忽略的字段

/*    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="post_date", length = 20, insertable=true, updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar postDate; // 创建时间

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name="changed_date", length = 20,insertable=false, updatable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedDate; // 修改时间

    @Column(name="user_enabled")
    private boolean enabled = true; // 指定默认值*/

    @Column(precision=8, scale=2)
    private float hourlyRate; // 设定精度

    /**
     * 元素集合 映射到数据库
     */
/*    @ElementCollection
    @CollectionTable(name="EMP_PHONE")
    @MapKeyColumn(name="PHONE_TYPE")
    @Column(name="PHONE_NUM")
    private Map<String, String> phoneNumbers;*/

    /*@NotNull
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="PO_ID")
    private PartyOrganization partyOrganization;*/

/*    @ManyToMany( cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable( name="sys_user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> roles;

    @ManyToMany( cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable( name="sys_user_group",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="group_id")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Group> groups;*/

/*    @NotNull
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="PO_ID")
    private PartyOrganization partyOrganization;*/

/*    @Basic(fetch=LAZY)
    @Lob @Column(name="PIC")
    private byte[] picture;*/

}

