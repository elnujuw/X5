package org.junle.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.junle.common.annotation.Excel;
import org.junle.common.annotation.EncryptField;

import java.io.Serializable;
import java.util.Map;

/**
 * 测试对象 demo_test
 * 
 * @author elnujuw
 * @date 2025-05-28
 */
@Data
@TableName(value = "demo_test")
public class DemoTest implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "object_id", type = IdType.AUTO)
    private Long objectId;

    /** 名称 */
    @Excel(name = "名称")
    @EncryptField(permi = "demo:test:sensitive")
    private String objectName;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
