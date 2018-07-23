package com.happinesea.ec.rws.lib.util;

import java.lang.reflect.Field

import org.apache.commons.beanutils.ConvertUtilsBean

/**
 * Bean操作用Utils
 */
public class BeanUtils {
    private static ConvertUtilsBean convetUtil = new ConvertUtilsBean()

    /**
     * {@link org.apache.commons.beanutils.BeanUtils#setProperty(Object, String, Object)}と同党な機能を提供するが、プロパティが大文字の場合、動作しない不具合をGroovyの機能で補足したものです。<br>
     * 
     * 
     * @param obj
     * @param propertyName
     * @param value
     * @throws MissingPropertyException
     */
    public static void setProperty(Object obj, String propertyName, Object value)
    throws MissingPropertyException{
	if(obj == null || !propertyName) {
	    return
	}

	if(value == null) {
	    obj[propertyName] = null
	    return
	}

	Class clz = obj.getClass()
	Field field = ClassUtils.getFieldApiResponse(clz, propertyName)
	if(ClassUtils.isPrimitve(field.getType())) {
	    if(field.getType() == value.getClass()) {
		obj[propertyName] = value
	    }else {
		obj[propertyName] = convetUtil.convert(value, field.getType())
	    }
	}else {
	    obj[propertyName] = value
	}
    }
}
