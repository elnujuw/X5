package org.junle.system.domain.core;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 基类实体
 *
 * @author elnujuw
 */
@Data
@Schema(description = "原型实体")
public class PrototypeEntity implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /** 搜索值 */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /** 主键 */
    @Schema(title = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 代码 */
    @Schema(title = "代码")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String reference;

    /** 名称 */
    @Schema(title = "名称")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String title;

    /** 描述 */
    @Schema(title = "描述")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String description;

    /** PortalType */
    @Schema(title = "PortalType")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String portalType;

    /** 创建者 */
    @Schema(title = "创建者")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String createBy;

    /** 创建时间 */
    @Schema(title = "创建时间")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @Schema(title = "更新者")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    private String updateBy;

    /** 更新时间 */
    @Schema(title = "更新时间")
    @TableField(updateStrategy= FieldStrategy.ALWAYS)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableLogic
    @Schema(title = "删除标志位")
    private String delFlag;
}
