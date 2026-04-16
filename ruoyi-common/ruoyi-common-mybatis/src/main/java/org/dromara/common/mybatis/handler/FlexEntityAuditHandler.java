package org.dromara.common.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.ObjectUtils;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.satoken.utils.LoginHelper;

import java.util.Date;

/**
 * MyBatis-Flex 实体自动填充处理器
 *
 * @author Lion Li
 */
@Slf4j
public class FlexEntityAuditHandler implements InsertListener, UpdateListener {

    private static final Long DEFAULT_USER_ID = -1L;

    @Override
    public void onInsert(Object entity) {
        try {
            if (ObjectUtil.isNotNull(entity) && entity instanceof BaseEntity baseEntity) {
                Date current = ObjectUtils.notNull(baseEntity.getCreateTime(), new Date());
                baseEntity.setCreateTime(current);
                baseEntity.setUpdateTime(current);
                if (ObjectUtil.isNull(baseEntity.getCreateBy())) {
                    LoginUser loginUser = getLoginUser();
                    if (ObjectUtil.isNotNull(loginUser)) {
                        Long userId = loginUser.getUserId();
                        baseEntity.setCreateBy(userId);
                        baseEntity.setUpdateBy(userId);
                        baseEntity.setCreateDept(ObjectUtils.notNull(baseEntity.getCreateDept(), loginUser.getDeptId()));
                    } else {
                        baseEntity.setCreateBy(DEFAULT_USER_ID);
                        baseEntity.setUpdateBy(DEFAULT_USER_ID);
                        baseEntity.setCreateDept(ObjectUtils.notNull(baseEntity.getCreateDept(), DEFAULT_USER_ID));
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    @Override
    public void onUpdate(Object entity) {
        try {
            if (ObjectUtil.isNotNull(entity) && entity instanceof BaseEntity baseEntity) {
                Date current = new Date();
                baseEntity.setUpdateTime(current);
                Long userId = LoginHelper.getUserId();
                if (ObjectUtil.isNotNull(userId)) {
                    baseEntity.setUpdateBy(userId);
                } else {
                    baseEntity.setUpdateBy(DEFAULT_USER_ID);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    private LoginUser getLoginUser() {
        try {
            return LoginHelper.getLoginUser();
        } catch (Exception e) {
            return null;
        }
    }

}
