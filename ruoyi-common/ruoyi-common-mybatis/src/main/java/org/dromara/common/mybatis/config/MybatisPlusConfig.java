package org.dromara.common.mybatis.config;

import com.mybatisflex.core.FlexGlobalConfig;
import org.dromara.common.core.factory.YmlPropertySourceFactory;
import org.dromara.common.mybatis.aspect.DataPermissionPointcutAdvisor;
import org.dromara.common.mybatis.handler.FlexEntityAuditHandler;
import org.dromara.common.mybatis.handler.MybatisExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Role;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-flex配置类
 *
 * @author Lion Li
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("${mybatis-flex.mapperPackage}")
@PropertySource(value = "classpath:common-mybatis.yml", factory = YmlPropertySourceFactory.class)
public class MybatisPlusConfig {

    @Bean
    public FlexEntityAuditHandler flexEntityAuditHandler() {
        FlexEntityAuditHandler handler = new FlexEntityAuditHandler();
        FlexGlobalConfig.getDefaultConfig().setInsertListener(handler);
        FlexGlobalConfig.getDefaultConfig().setUpdateListener(handler);
        return handler;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public DataPermissionPointcutAdvisor dataPermissionPointcutAdvisor() {
        return new DataPermissionPointcutAdvisor();
    }

    @Bean
    public MybatisExceptionHandler mybatisExceptionHandler() {
        return new MybatisExceptionHandler();
    }

}
