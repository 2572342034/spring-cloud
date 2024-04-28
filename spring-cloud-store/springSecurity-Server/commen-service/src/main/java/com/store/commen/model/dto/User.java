package com.store.commen.model.dto;

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
@TableName(value = "sys_user")
@ApiModel(description = "表单登录实体")
public class User implements Serializable {
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
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 年龄
     */
    private String age;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 账号状态（0正常 1停用）
     */
    private UserStatus status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 余额
     */
    private Integer balance;
    /**
     * 用户性别（0男，1女，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0管理员，1普通用户）
     */
    private String userType;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
}