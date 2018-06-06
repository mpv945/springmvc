package org.haijun.study.model.entity;

import lombok.Data;
import org.haijun.study.model.BaseEntity;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_card")//身份证
public class IdentityCard extends BaseEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)// mysql 自增列
    private Long id;

    //@Column(columnDefinition = (" comment '身份证编码'"))
    private String code;

    // 如果不需要双向关联，字段不需要写，mappedBy = "identityCard" ，identityCard为对方定义的字段名
    @OneToOne(mappedBy = "identityCard") //mappedBy相当于inverse=true的含义: 由双向关联另一方维护该关联,己方不维护该关联(只能进行查询操作)
    private User user;
}
