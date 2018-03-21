package com.happinesea.ec.rws.lib.util;

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

import groovy.util.logging.Log4j2

/**
 * Class操作関連のUtils
 * 
 * @author loveapple
 *
 */
@Log4j2
public class ClassUtils extends org.apache.commons.lang.ClassUtils {

    private static final String NO_TARGET_METHOD_FIX = '_'

    /**
     * 親クラスの定義を含めて、{@linkplain ApiResponse}型、および、プリミティブ型の{@linkplain Field}を結果に戻す。
     * 
     * @param clz 対象クラス
     * @return {@linkplain Field}の配列を戻す。{@linkplain ApiResponse}型がない場合、空の配列を戻す
     */
    public static Field[] getFieldsApiResponse(Class clz) {
	if(clz == null) {
	    throw new IllegalArgumentException('clz is null.')
	}

	Field[] fields = clz.getDeclaredFields()

	Field[] result = []

	for(Field field : fields) {

	    if(log.isDebugEnabled()) {
		log.debug('Field type : {} -> field: {}',
			field.getType(),
			field.getName())
	    }

	    if(String.valueOf(field.getType()).toUpperCase().endsWith('ENUM')) {
		if(log.isDebugEnabled()) {
		    log.debug('add enum [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(ArrayUtils.contains(field.getType().getInterfaces(), ApiResponseNode.class)) {
		if(log.isDebugEnabled()) {
		    log.debug('add api response node [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if((field.getType().isPrimitive() || String.class == field.getType() ) && !field.getName().startsWith(NO_TARGET_METHOD_FIX)) {
		if(log.isDebugEnabled()) {
		    log.debug('add primitive(and String) [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }
	}

	if(clz.getSuperclass() != null ) {
	    Field[] tmp = getFieldsApiResponse(clz.getSuperclass())
	    if(!ArrayUtils.isEmpty(tmp)) {
		for(Field f : tmp) {
		    result = ArrayUtils.add(result, f)
		}
	    }
	}

	return result
    }
}
