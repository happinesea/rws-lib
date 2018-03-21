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

    private static final Set<Class> PRIMITIVE_WARPPER;
    static {
	PRIMITIVE_WARPPER = new HashSet()
	PRIMITIVE_WARPPER.add(Boolean)
	PRIMITIVE_WARPPER.add(Character)
	PRIMITIVE_WARPPER.add(Byte)
	PRIMITIVE_WARPPER.add(Short)
	PRIMITIVE_WARPPER.add(Integer)
	PRIMITIVE_WARPPER.add(Long)
	PRIMITIVE_WARPPER.add(Float)
	PRIMITIVE_WARPPER.add(Double)
    }

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

	    if(isApiResponseEnum(field.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('add enum [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(isApiResponseNode(field.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('add api response node [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(isPrimitveAndString(field.getType())) {
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

    /**
     * {@linkplain ApiResponse}の実装クラスかどうかを判定する
     * 
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isApiResponseNode(Class clz) {

	return isTargetInterface(clz, ApiResponseNode)
    }

    /**
     * <s>{@linkplain ApiResponseEnum}の実装クラスかどうかを判定する</s>
     *
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isApiResponseEnum(Class clz) {
	if(clz == null) {
	    return false
	}

	return String.valueOf(clz).toUpperCase().endsWith('ENUM')
    }

    /**
     * {@linkplain String}、又は、プリミティブ型のラッパークラスかどうかを判定する
     * 
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isPrimitveAndString(Class clz) {
	if(clz == null) {
	    return false
	}

	return (String.class == clz || PRIMITIVE_WARPPER.contains(clz)) && !clz.getName().startsWith(NO_TARGET_METHOD_FIX)
    }

    /**
     * 対象のIFの実装クラスかどうかを判定する
     * 
     * @param clz 判定クラス
     * @param targetIf 対象のIF
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isTargetInterface(Class clz, Class targetIf) {
	if(clz == null || targetIf == null || ArrayUtils.isEmpty(clz.getInterfaces())) {
	    return false;
	}

	return ArrayUtils.contains(clz.getInterfaces(), targetIf)
    }
}
