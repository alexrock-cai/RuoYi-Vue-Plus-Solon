package org.dromara.common.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mybatis.handler.PlusDataPermissionHandler;

/**
 * 数据权限拦截器(MyBatis-Flex版本)
 *
 * @author Lion Li
 */
@Slf4j
public class PlusDataPermissionInterceptor {

    private final PlusDataPermissionHandler dataPermissionHandler = new PlusDataPermissionHandler();

}
