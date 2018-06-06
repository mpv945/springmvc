package org.haijun.study.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    /**
     * 唯一ID
     * @return
     */
    public abstract Long getId();

    //@JsonIgnore
    @CreatedBy
    @Column(name = "create_user")
    private String createUser;

    //@JsonIgnore
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    //@JsonIgnore
    @LastModifiedBy
    @Column(name = "update_user")
    private String updateUser;

    //@JsonIgnore
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    //@JsonIgnore
    @Column(name = "is_deleted", nullable = false)//, columnDefinition = (" comment '删除标记'")
    private boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        this.createTime = LocalDateTime.now();
        //默认插入的数据，删除标记为0
        this.isDeleted=false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateTime = LocalDateTime.now();
        //this.isDeleted="0";
    }

}
