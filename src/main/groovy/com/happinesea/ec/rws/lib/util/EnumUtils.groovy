package com.happinesea.ec.rws.lib.util

import groovy.util.logging.Log4j2

/**
 * @author loveapple
 *
 */
@Log4j2
class EnumUtils {
    public static <T> T getApiResponseEnum(Class<T> clz, String code) {
	if(clz == null || code == null) {
	    throw new IllegalArgumentException('Enum class or code is null')
	}
	if(!ClassUtils.isApiResponseEnum(clz)) {
	    log.debug('{} -> {}', ClassUtils.isApiResponseEnum(clz), clz)
	    throw new IllegalArgumentException('{} is not ApiResponseEnum.', clz)
	}

	for(def it : clz.getEnumConstants()) {
	    if(it.toString() == code) {
		return it
	    }
	}


	throw new IllegalArgumentException('No enum constant {} in {}', code, clz)
    }
}