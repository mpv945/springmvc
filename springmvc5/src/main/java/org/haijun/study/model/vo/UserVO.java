package org.haijun.study.model.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 用户信息
 */
@Data
@Api(tags = "UserVO", description = "用户信息")
public class UserVO {

    /**
     * 姓名
     */
    @NotBlank(message = "{validator.user.name.null.message}")
    @ApiModelProperty(value = "用户名称", allowEmptyValue = true)
    private String name;

    /**
     * 年龄
     */
    @Size(max=150 ,min = 1, message = "{validator.user.age.size.message}")
    @ApiModelProperty(value = "用户年龄")
    private long age;

    /**
     * 生日
     */
    @ApiModelProperty(value = "用户生日")
    private Date birthday;
}
