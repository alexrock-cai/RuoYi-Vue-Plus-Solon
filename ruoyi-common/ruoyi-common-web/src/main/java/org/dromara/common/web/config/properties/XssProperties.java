package org.dromara.common.web.config.properties;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;

import java.util.ArrayList;
import java.util.List;

/**
 * xss过滤 配置属性
 *
 * @author Lion Li
 */
@Data
@PropsSet(prefix = "xss")
public class XssProperties {

    /**
     * Xss开关
     */
    private Boolean enabled;

    /**
     * 排除路径
     */
    private List<String> excludeUrls = new ArrayList<>();

}
