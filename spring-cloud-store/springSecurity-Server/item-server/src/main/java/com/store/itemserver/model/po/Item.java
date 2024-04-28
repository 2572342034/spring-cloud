package com.store.itemserver.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@ApiModel(description = "商品实体")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item")
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @ApiModelProperty("商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SKU名称
     */
    @ApiModelProperty("SKU名称")
    private String name;

    /**
     * 价格（分）
     */
    @ApiModelProperty("价格（分）")
    private Integer price;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stock;

    /**
     * 商品图片
     */
    @ApiModelProperty("商品图片")
    private String image;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String category;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brand;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 销量
     */
    @ApiModelProperty("销量")
    private Integer sold;

    /**
     * 评论数
     */
    @ApiModelProperty("评论数")
    private Integer commentCount;

    /**
     * 是否是推广广告，true/false
     */
    @ApiModelProperty("是否是推广广告，true/false")
    @TableField("isAD")
    private Boolean isAD;

    /**
     * 商品状态 1-正常，2-下架，3-删除
     */
    @ApiModelProperty("商品状态 1-正常，2-下架，3-删除")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty("商品创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("商品更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty("商品创建人")
    private Long creater;

    /**
     * 修改人
     */
    @ApiModelProperty("商品修改人")
    private Long updater;


}
