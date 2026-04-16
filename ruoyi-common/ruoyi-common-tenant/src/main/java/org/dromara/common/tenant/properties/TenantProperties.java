package org.dromara.common.tenant.properties;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;

import java.util.List;

/**
 * 租户 配置属性
 *
 * @author Lion Li
 */
@Data
@PropsSet(prefix = "tenant")
public class TenantProperties {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 排除表
     */
    private List<String> excludes;

}
