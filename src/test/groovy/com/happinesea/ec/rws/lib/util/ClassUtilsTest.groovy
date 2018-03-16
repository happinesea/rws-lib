package com.happinesea.ec.rws.lib.util
import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsItemGetResult
import com.happinesea.ec.rws.lib.bean.RwsItemResponseResult
import com.happinesea.ec.rws.lib.bean.RwsResponseResult
import com.happinesea.ec.rws.lib.bean.RwsResponseResult.Status

class ClassUtilsTest {

    @Test
    public void testGetFieldsApiResponse() {
	def expectedException = shouldFail(IllegalArgumentException){ ClassUtils.getFieldsApiResponse(null) }

	assertEquals 'clz is null.', expectedException.message

	assertTrue  ArrayUtils.isEmpty(ClassUtils.getFieldsApiResponse(List.class))

	// Test have not superclass

	Field[] fields = ClassUtils.getFieldsApiResponse(RwsResponseResult.class)

	assertEquals 1, fields.length
	assertEquals 'status', fields[0].getName()
	assertEquals Status.class, fields[0].getType()

	// Test have superclass
	fields = ClassUtils.getFieldsApiResponse(RwsItemResponseResult.class)
	assertEquals 'status', fields[1].getName()
	assertEquals Status.class, fields[1].getType()
	assertEquals 'itemGetResult', fields[0].getName()
	assertEquals RwsItemGetResult.class, fields[0].getType()

	assertEquals 2, fields.length
    }
}
