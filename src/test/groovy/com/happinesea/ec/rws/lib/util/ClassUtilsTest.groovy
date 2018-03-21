package com.happinesea.ec.rws.lib.util
import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsResponseParser
import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsItemGetResult
import com.happinesea.ec.rws.lib.bean.RwsItemResponseResult
import com.happinesea.ec.rws.lib.bean.RwsResponseResult
import com.happinesea.ec.rws.lib.bean.RwsResponseResult.Status
import com.happinesea.ec.rws.lib.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

class ClassUtilsTest {

    @Test
    public void testGetFieldsApiResponse() {
	// null check
	def expectedException = shouldFail(IllegalArgumentException){ ClassUtils.getFieldsApiResponse(null) }

	assertEquals 'clz is null.', expectedException.message

	assertTrue  ArrayUtils.isEmpty(ClassUtils.getFieldsApiResponse(List))

	// Test have not superclass

	Field[] fields = ClassUtils.getFieldsApiResponse(RwsResponseResult)

	assertEquals 1, fields.length
	assertEquals 'status', fields[0].getName()
	assertEquals Status, fields[0].getType()

	// Test have superclass
	fields = ClassUtils.getFieldsApiResponse(RwsItemResponseResult)
	assertEquals 'status', fields[1].getName()
	assertEquals Status, fields[1].getType()
	assertEquals 'itemGetResult', fields[0].getName()
	assertEquals RwsItemGetResult, fields[0].getType()

	assertEquals 2, fields.length

	fields = ClassUtils.getFieldsApiResponse(Status)

	// Test have primitave and string
	assertEquals 5, fields.length
	assertEquals 'requests', fields[4].getName()
	assertEquals String, fields[4].getType()
	assertEquals 'requestId', fields[3].getName()
	assertEquals String, fields[3].getType()
	assertEquals 'message', fields[2].getName()
	assertEquals MessageElementEnum, fields[2].getType()
	assertEquals 'systemStatus', fields[1].getName()
	assertEquals SystemStatusElementEnum, fields[1].getType()
	assertEquals 'interfaceId', fields[0].getName()
	assertEquals String, fields[0].getType()

    }

    @Test
    public void testIsApiResponseNode() {
	assertFalse ClassUtils.isApiResponseNode(null)

	assertFalse ClassUtils.isApiResponseNode(ApiResponseNode)
	assertFalse ClassUtils.isApiResponseNode(RwsResponseParser)

	assertTrue ClassUtils.isApiResponseNode(Status)
    }

    @Test
    public void testIsApiResponseEnum() {
	assertFalse ClassUtils.isApiResponseEnum(null)

	assertFalse ClassUtils.isApiResponseEnum(ApiResponseNode)
	assertFalse ClassUtils.isApiResponseEnum(Status)

	assertTrue ClassUtils.isApiResponseEnum(SystemStatusElementEnum)
    }

    @Test
    public void testIsTargetInterface() {
	assertFalse ClassUtils.isTargetInterface(null, null)
	assertFalse ClassUtils.isTargetInterface(SystemStatusElementEnum, null)
	assertFalse ClassUtils.isTargetInterface(null, SystemStatusElementEnum)

	assertTrue ClassUtils.isTargetInterface(String, CharSequence)

    }

    @Test
    public void testIsPrimimitveAndString() {
	assertFalse ClassUtils.isPrimitveAndString(null)

	assertFalse ClassUtils.isPrimitveAndString(ApiResponseNode)
	assertFalse ClassUtils.isPrimitveAndString(Status)

	assertTrue ClassUtils.isPrimitveAndString(Integer)
	assertTrue ClassUtils.isPrimitveAndString(String)

    }
}
