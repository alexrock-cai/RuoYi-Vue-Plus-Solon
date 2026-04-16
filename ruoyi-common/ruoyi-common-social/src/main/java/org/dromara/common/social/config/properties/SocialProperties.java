package org.dromara.common.social.config.properties;

import lombok.Data;
import org.noear.solon.annotation.PropsSet;
import org.noear.solon.annotation.Component;

import java.util.Map;

/**
 * Social 配置属性
 *
 * @author thiszhc
 */
@Data
@Component
@PropsSet(prefix = "justauth")
public class SocialProperties {

    /**
     * 授权类型
     */
    private Map<String, SocialLoginConfigProperties> type;

}
