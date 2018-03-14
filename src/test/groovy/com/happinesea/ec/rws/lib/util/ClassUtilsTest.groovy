package com.happinesea.ec.rws.lib.util
import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.commons.lang.ArrayUtils
import org.junit.Test

class ClassUtilsTest {

    @Test
    public void testGetFieldsApiResponse() {
	def expectedException = shouldFail(IllegalArgumentException){ ClassUtils.getFieldsApiResponse(null) }

	assertEquals 'clz is null.', expectedException.message

	assertTrue  ArrayUtils.isEmpty(ClassUtils.getFieldsApiResponse(List.class))

	// TODO go on test code
    }
}
