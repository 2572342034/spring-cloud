package com.store.addressserver.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@ApiModel(description = "收货地址实体")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 县/区
     */
    @ApiModelProperty("县/区")
    private String town;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String mobile;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String street;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contact;

    /**
     * 是否是默认 1默认 0否
     */
    @ApiModelProperty("是否是默认 1默认 0否")
    private Integer isDefault;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String notes;


}
