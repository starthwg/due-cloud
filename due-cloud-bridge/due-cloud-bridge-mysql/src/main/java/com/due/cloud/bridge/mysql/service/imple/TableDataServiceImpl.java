package com.due.cloud.bridge.mysql.service.imple;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.due.cloud.bridge.mysql.domian.TableData;
import com.due.cloud.bridge.mysql.mapper.ITableDataMapper;
import com.due.cloud.bridge.mysql.service.ITableDataService;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class TableDataServiceImpl<T extends TableData, E extends ITableDataMapper<T>> extends BasicDataServiceImpl<T, E> implements ITableDataService<T> {
    private static final int DEFAULT_BATCH_SIZE = 1000;

    protected boolean create(T entity) {
        return SqlHelper.retBool(this.baseMapper.insert(entity));
    }

    protected boolean createBatch(Collection<T> entityList) {
        return createBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    protected boolean createBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    protected boolean createOrUpdateBatch(Collection<T> entityList) {
        return createOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    protected boolean createOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return SqlHelper.saveOrUpdateBatch(this.entityClass, this.mapperClass, this.log, entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal) || CollectionUtils.isEmpty(sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        }, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(getSqlStatement(SqlMethod.UPDATE_BY_ID), param);
        });
    }

    protected boolean removeById(Serializable id) {
        return SqlHelper.retBool(this.baseMapper.deleteById(id));
    }

    protected boolean removeByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        return SqlHelper.retBool(this.baseMapper.deleteByMap(columnMap));
    }

    protected boolean remove(Wrapper<T> queryWrapper) {
        return SqlHelper.retBool(this.baseMapper.delete(queryWrapper));
    }

    protected boolean removeByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return SqlHelper.retBool(this.baseMapper.deleteBatchIds(idList));
    }

    protected boolean updateById(T entity) {
        return SqlHelper.retBool(this.baseMapper.updateById(entity));
    }

    protected boolean update(Wrapper<T> updateWrapper) {
        return update(null, updateWrapper);
    }

    protected boolean update(T entity, Wrapper<T> updateWrapper) {
        return SqlHelper.retBool(this.baseMapper.update(entity, updateWrapper));
    }

    protected boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, DEFAULT_BATCH_SIZE);
    }

    protected boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    protected boolean createOrUpdate(T entity) {
        if (null != entity) {
            TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(this.selectById((Serializable) idVal)) ? this.create(entity) : this.updateById(entity);
        }
        return false;
    }

    protected LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return ChainWrappers.lambdaUpdateChain(this.baseMapper);
    }

    private String getSqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.getSqlStatement(mapperClass, sqlMethod);
    }

    private <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.entityClass, this.log, list, batchSize, consumer);
    }
}
