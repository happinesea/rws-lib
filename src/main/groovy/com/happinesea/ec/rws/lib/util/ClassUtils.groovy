package com.happinesea.ec.rws.lib.util;

import java.lang.reflect.Field
import java.util.HashSet

import org.apache.commons.lang.ArrayUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.ApiResponseEnum

import groovy.util.logging.Log4j2

/**
 * Class操作関連のUtils
 * 
 * 
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
	    }else if(isTargetInterface(field.getType(), Collection)) {
		if(log.isDebugEnabled()) {
		    log.debug('add collection [{}] to result.', field)
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

	return isTargetInterface(clz, ApiResponseEnum)
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

    /**
     * 対象{@linkplain Field}のジェネクスの型を取得する<br>
     * ジェネクスが定義されないものは自分自身の型を戻す
     * 
     * @param field
     * @return
     */
    public static Class getFieldGenertics(Field field) {
	if(field == null) {
	    throw new IllegalArgumentException('field is null.')
	}

	if(ClassUtils.isPrimitveAndString(field.getType())) {
	    return field.getType()
	}
	String typeName = field.getGenericType().getTypeName()

	int startPoint = typeName.indexOf('<')
	if(startPoint < 0) {
	    return field.getType()
	}

	int endPoint = typeName.indexOf('>')
	if(endPoint < 0) {
	    throw new RuntimeException(String.format('invalid class name of [{}]', typeName))
	}

	String targetName = typeName.substring(startPoint + 1, endPoint)

	if(targetName == '?') {
	    return field.getType()
	}else {
	    Class.forName(targetName)
	}
    }
}
