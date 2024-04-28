package com.store.cartserver.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单详情表
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@ApiModel(description = "购物车po实体")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车条目id 
     */
    @ApiModelProperty("购物车条目id ")
    @TableId(value = "id", type = IdType.AUTO)

    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id",required = true)
    private Long userId;

    /**
     * sku商品id
     */
    @NotNull(message = "商品id不能为空")
    @ApiModelProperty(value = "sku商品id",required = true)
    private Long itemId;

    /**
     * 购买数量
     */
    @ApiModelProperty("购买数量")
    private Integer num;

    /**
     * 商品标题
     */
    @ApiModelProperty("商品标题")
    private String name;

    /**
     * 商品动态属性键值集
     */
    @ApiModelProperty("商品动态属性键值集")
    private String spec;

    /**
     * 价格,单位：分
     */
    @ApiModelProperty("价格,单位：分")
    private Integer price;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String image;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
