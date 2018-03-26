package com.happinesea.ec.rws.lib.util

import groovy.util.logging.Log4j2

/**
 * Enumを操作するUtil
 *
 */
@Log4j2
class EnumUtils {
    public static <T> T getApiResponseEnum(Class<T> clz, String code) {
	if(clz == null || code == null) {
	    throw new IllegalArgumentException('Enum class or code is null')
	}
	if(!ClassUtils.isApiResponseEnum(clz)) {
	    throw new IllegalArgumentException(String.format('%s is not ApiResponseEnum.', clz.getName()))
	}

	for(def it : clz.getEnumConstants()) {
	    if(it.toString() == code) {
		return it
	    }
	}

	throw new IllegalArgumentException(String.format('No enum constant %s in %s', code, clz.getName()))
    }
}