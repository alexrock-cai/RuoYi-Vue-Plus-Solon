package org.dromara.common.mybatis.core.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.ClassUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StreamUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 自定义 Mapper 接口, 实现 自定义扩展
 *
 * @param <T> table 泛型
 * @param <V> vo 泛型
 * @author Lion Li
 * @since 2021-05-13
 */
@SuppressWarnings("unchecked")
public interface BaseMapperPlus<T, V> extends BaseMapper<T> {

    Log log = LogFactory.getLog(BaseMapperPlus.class);

    default Class<V> currentVoClass() {
        return (Class<V>) ClassUtil.getGenericClass(this.getClass(), BaseMapperPlus.class, 1);
    }

    default Class<T> currentModelClass() {
        return (Class<T>) ClassUtil.getGenericClass(this.getClass(), BaseMapperPlus.class, 0);
    }

    default List<T> selectList() {
        return this.selectListByQuery(QueryWrapper.create());
    }

    default boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 1000);
    }

    default boolean insertBatch(Collection<T> entityList, int batchSize) {
        if (CollUtil.isEmpty(entityList)) {
            return true;
        }
        entityList.forEach(this::insert);
        return true;
    }

    default boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 1000);
    }

    default boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollUtil.isEmpty(entityList)) {
            return true;
        }
        entityList.forEach(this::update);
        return true;
    }

    default boolean insertOrUpdateBatch(Collection<T> entityList) {
        return insertOrUpdateBatch(entityList, 1000);
    }

    default boolean insertOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (CollUtil.isEmpty(entityList)) {
            return true;
        }
        entityList.forEach(this::insertOrUpdate);
        return true;
    }

    default V selectVoById(Serializable id) {
        return selectVoById(id, this.currentVoClass());
    }

    default <C> C selectVoById(Serializable id, Class<C> voClass) {
        T obj = this.selectOneById(id);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        return MapstructUtils.convert(obj, voClass);
    }

    default List<V> selectVoByIds(Collection<? extends Serializable> idList) {
        return selectVoByIds(idList, this.currentVoClass());
    }

    default <C> List<C> selectVoByIds(Collection<? extends Serializable> idList, Class<C> voClass) {
        List<T> list = this.selectListByIds(idList);
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return MapstructUtils.convert(list, voClass);
    }

    default List<V> selectVoByMap(Map<String, Object> map) {
        return selectVoByMap(map, this.currentVoClass());
    }

    default <C> List<C> selectVoByMap(Map<String, Object> map, Class<C> voClass) {
        QueryWrapper qw = QueryWrapper.create();
        map.forEach(qw::eq);
        List<T> list = this.selectListByQuery(qw);
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return MapstructUtils.convert(list, voClass);
    }

    default V selectVoOne(QueryWrapper wrapper) {
        return selectVoOne(wrapper, this.currentVoClass());
    }

    default V selectVoOne(QueryWrapper wrapper, boolean throwEx) {
        return selectVoOne(wrapper, this.currentVoClass());
    }

    default <C> C selectVoOne(QueryWrapper wrapper, Class<C> voClass) {
        T obj = this.selectOneByQuery(wrapper);
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        return MapstructUtils.convert(obj, voClass);
    }

    default <C> C selectVoOne(QueryWrapper wrapper, Class<C> voClass, boolean throwEx) {
        return selectVoOne(wrapper, voClass);
    }

    default List<V> selectVoList() {
        return selectVoList(QueryWrapper.create(), this.currentVoClass());
    }

    default List<V> selectVoList(QueryWrapper wrapper) {
        return selectVoList(wrapper, this.currentVoClass());
    }

    default <C> List<C> selectVoList(QueryWrapper wrapper, Class<C> voClass) {
        List<T> list = this.selectListByQuery(wrapper);
        if (CollUtil.isEmpty(list)) {
            return CollUtil.newArrayList();
        }
        return MapstructUtils.convert(list, voClass);
    }

    default <P extends Page<V>> P selectVoPage(Page<T> page, QueryWrapper wrapper) {
        return selectVoPage(page, wrapper, this.currentVoClass());
    }

    default <C, P extends Page<C>> P selectVoPage(Page<T> page, QueryWrapper wrapper, Class<C> voClass) {
        Page<T> result = this.paginate(page, wrapper);
        Page<C> voPage = new Page<>(result.getPageNumber(), result.getPageSize(), result.getTotalRow());
        if (CollUtil.isEmpty(result.getRecords())) {
            return (P) voPage;
        }
        voPage.setRecords(MapstructUtils.convert(result.getRecords(), voClass));
        return (P) voPage;
    }

    default <C> List<C> selectObjs(QueryWrapper wrapper, Function<? super Object, C> mapper) {
        return StreamUtils.toList(this.selectObjectListByQuery(wrapper), mapper);
    }

}
