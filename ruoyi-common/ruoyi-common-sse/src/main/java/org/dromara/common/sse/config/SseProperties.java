package org.dromara.common.sse.config;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;

/**
 * SSE 配置项
 *
 * @author Lion Li
 */
@Data
@PropsSet("sse")
public class SseProperties {

    private Boolean enabled;

    /**
     * 路径
     */
    private String path;
}
