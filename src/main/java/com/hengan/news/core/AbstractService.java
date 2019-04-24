package com.hengan.news.core;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Resource
    protected MyMapper<T> myMapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        myMapper.insertSelective(model);
    }

    @Override
    public void save(List<T> models) {
        myMapper.insertList(models);
    }

    @Override
    public void deleteById(Integer id) {
        myMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        myMapper.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        myMapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return myMapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return myMapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return myMapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return myMapper.selectAll();
    }
}
