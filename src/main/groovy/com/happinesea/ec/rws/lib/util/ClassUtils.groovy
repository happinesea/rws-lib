package com.happinesea.ec.rws.lib.util;

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * Class操作関連のUtils
 * 
 * @author loveapple
 *
 */
public class ClassUtils {

    /**
     * 親クラスの定義を含めて、{@linkplain ApiResponse}型の{@linkplain Field}を結果に戻す。
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
	    if(field.getType().equals(ApiResponseNode.class)) {
		result[] = field
	    }
	}

	if(clz.getSuperclass() != null ) {
	    Field[] tmp = getFields(clz.getSuperclass())
	    if(!ArrayUtils.isEmpty(tmp)) {
		ArrayUtils.add(result, tmp)
	    }
	}

	return fields
    }
}
