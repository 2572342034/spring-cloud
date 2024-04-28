package com.store.orderserver.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(description = "订单详情页实体类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单详情id 
     */
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private String orderId;

    /**
     * sku商品id
     */
    @ApiModelProperty("商品id")
    private Long itemId;

    /**
     * 购买数量
     */
    @ApiModelProperty("销量")
    private Integer num;

    /**
     * 商品标题
     */
    @ApiModelProperty("SKU名称")
    private String name;

    /**
     * 商品动态属性键值集
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 价格,单位：分
     */
    @ApiModelProperty("价格（分）")
    private Integer price;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String image;

    /**
     * 创建时间
     */
    @ApiModelProperty("订单创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("订单更新时间")
    private LocalDateTime updateTime;
    /**
     * 状态
     * 1.未支付 2.已支付
     */
    @ApiModelProperty("支付状态")
    private Integer status;

}
