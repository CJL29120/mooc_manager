package com.chen.mooc_manager.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class ParamUtil<T> {

    @SuppressWarnings("unchecked")
    public T afterTrim(T entity){
        Field[] fields = entity.getClass().getDeclaredFields();

        try {
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);
                String type = fields[i].getGenericType().toString();
                if (type.equals("class java.lang.String")){
                    Method method = entity.getClass().getMethod("get"+name);
                     String value = (String) method.invoke(entity);
                     if(null != value){
                         method = entity.getClass().getMethod("set"+name,String.class);
                         method.invoke(entity,StringUtils.trimAllWhitespace(value));
                     }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
