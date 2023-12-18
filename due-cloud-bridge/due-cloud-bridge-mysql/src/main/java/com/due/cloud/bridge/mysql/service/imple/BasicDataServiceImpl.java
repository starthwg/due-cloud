package com.due.cloud.bridge.mysql.service.imple;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.due.basic.tookit.doamin.PageSet;
import com.due.basic.tookit.utils.LogicUtil;
import com.due.cloud.bridge.mysql.domian.BasicData;
import com.due.cloud.bridge.mysql.mapper.IBasicDataMapper;
import com.due.cloud.bridge.mysql.service.IBasicDataService;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicDataServiceImpl<T extends BasicData, E extends IBasicDataMapper<T>> implements IBasicDataService<T> {


    @Autowired
    protected E baseMapper;

    protected Log log = LogFactory.getLog(getClass());
    protected Class<?> entityClass = currentModelClass();
    protected Class<?> mapperClass = currentMapperClass();


    private Class<T> currentMapperClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }

    @SuppressWarnings("unchecked")
    private Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 获取一个数据
     */
    public T selectOne(Wrapper<T> wrapper, boolean throwException) {
        if (throwException) {
            return this.baseMapper.selectOne(wrapper);
        } else {
            return SqlHelper.getObject(log, this.baseMapper.selectList(wrapper));
        }
    }

    public Map<String, Object> selectMap(Wrapper<T> wrapper) {
        return SqlHelper.getObject(log, this.baseMapper.selectMaps(wrapper));
    }

    protected <E> E selectConvert(Wrapper<T> wrapper, Function<? super Object, E> function) {
        return SqlHelper.getObject(log, this.selectObjectAsList(wrapper, function));
    }

    protected <V> List<V> selectObjectAsList(Wrapper<T> wrapper, Function<? super Object, V> mapper) {
        return this.baseMapper.selectObjs(wrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    protected T selectById(Serializable id) {
        return this.baseMapper.selectById(id);
    }

    protected List<T> selectByIdList(Collection<? extends Serializable> idList) {
        return this.baseMapper.selectBatchIds(idList);
    }

    protected LambdaQueryChainWrapper<T> lambdaQuery() {
        return ChainWrappers.lambdaQueryChain(this.baseMapper);
    }

    protected Integer selectCount(Wrapper<T> wrapper) {
        return SqlHelper.retCount(this.baseMapper.selectCount(wrapper));
    }

    protected int selectCount() {
        return this.selectCount(Wrappers.emptyWrapper());
    }


    protected List<T> toList(LambdaQueryChainWrapper<T> lambdaQueryChainWrapper) {
        if (null == lambdaQueryChainWrapper) return Collections.emptyList();
        List<T> list = lambdaQueryChainWrapper.list();
        if (LogicUtil.isEmpty(list)) return Collections.emptyList();
        return list;
    }

    protected List<T> toList(LambdaQueryWrapper<T> wrapper) {
        List<T> list = this.baseMapper.selectList(wrapper);
        if (null == list || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }

    protected PageSet<T> toPage(PageSet<T> pageSet, LambdaQueryChainWrapper<T> wrapper) {
        Page<T> page = this.toPage(pageSet);
        page = wrapper.page(page);
        pageSet.setTotal(page.getTotal());
        pageSet.setRecords(page.getRecords());
        return pageSet;
    }

    protected <T, R> PageSet<R> toPage(PageSet<T> pageSet, Function<T, R> converter) {
        if (pageSet == null) return null;
        PageSet<R> page = new PageSet<>();
        page.setStart(pageSet.getStart());
        page.setLimit(pageSet.getLimit());
        page.setTotal(pageSet.getTotal());
        List<T> records = pageSet.getRecords();
        if (records != null && !records.isEmpty()) {
            page.setRecords(records.stream().map(converter).collect(Collectors.toList()));
        }
        return page;
    }

    protected PageSet<T> toPage(PageSet<T> pageSet, Wrapper<T> wrapper) {
        Page<T> page = this.toPage(pageSet);
        page = this.baseMapper.selectPage(page, wrapper);
        pageSet.setTotal(page.getTotal());
        pageSet.setRecords(page.getRecords());
        return pageSet;
    }

    protected Page<T> toPage(PageSet<T> pageSet) {
        long current = (pageSet.getStart() <= 0 ? 0 : (pageSet.getStart() / pageSet.getLimit())) + 1;
        Page<T> page = new Page<>(current, pageSet.getLimit());
        return page;
    }
}
