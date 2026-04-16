package org.dromara.common.web.config;

import org.dromara.common.web.config.properties.CaptchaProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;


/**
 * 验证码配置
 *
 * @author Lion Li
 */
@AutoConfiguration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfig {

}
