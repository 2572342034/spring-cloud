package com.store.userserver.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.store.commen.enums.UserStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户表(User)实体类
 *
 * @author LFJ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "表单登录实体")
public class UserLoginFromVO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    @NotNull(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty(value = "密码",required = true)
    @NotNull(message = "密码不能为空")
    private String password;

}