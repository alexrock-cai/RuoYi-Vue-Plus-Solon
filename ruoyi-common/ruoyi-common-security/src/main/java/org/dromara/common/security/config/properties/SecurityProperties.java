package org.dromara.common.security.config.properties;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;

/**
 * Security 配置属性
 *
 * @author Lion Li
 */
@Data
@PropsSet(prefix = "security")
public class SecurityProperties {

    /**
     * 排除路径
     */
    private String[] excludes;


}
