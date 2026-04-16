package org.dromara.common.websocket.config.properties;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;

/**
 * WebSocket 配置项
 *
 * @author zendwang
 */
@PropsSet("websocket")
@Data
public class WebSocketProperties {

    private Boolean enabled;

    /**
     * 路径
     */
    private String path;

    /**
     *  设置访问源地址
     */
    private String allowedOrigins;
}
