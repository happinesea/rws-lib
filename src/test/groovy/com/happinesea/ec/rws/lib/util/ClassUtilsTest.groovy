package com.happinesea.ec.rws.lib.util
import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsResponseParser
import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.RwsResponseXmlResult.Status
import com.happinesea.ec.rws.lib.bean.node.RwsItemGetResponseResult
import com.happinesea.ec.rws.lib.bean.node.RwsItemGetResult
import com.happinesea.ec.rws.lib.bean.node.RwsItemSearchResult
import com.happinesea.ec.rws.lib.bean.node.RwsResponseItem
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

	Field[] fields = ClassUtils.getFieldsApiResponse(RwsResponseXmlResult)

	assertEquals 1, fields.length
	assertEquals 'status', fields[0].getName()
	assertEquals Status, fields[0].getType()

	// Test have superclass
	fields = ClassUtils.getFieldsApiResponse(RwsItemGetResponseResult)
	assertEquals 'status', fields[1].getName()
	assertEquals Status, fields[1].getType()
	assertEquals 'itemGetResult', fields[0].getName()
	assertEquals RwsItemGetResult, fields[0].getType()

	assertEquals 2, fields.length

	fields = ClassUtils.getFieldsApiResponse(Status)

	// Test have primitave and string
	assertEquals 5, fields.length
	assertEquals 'requests', fields[4].getName()
	assertEquals List, fields[4].getType()
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

	assertTrue ClassUtils.isTargetInterface(List, Collection)

    }

    @Test
    public void testIsPrimimitveAndString() {
	assertFalse ClassUtils.isPrimitveAndString(null)

	assertFalse ClassUtils.isPrimitveAndString(ApiResponseNode)
	assertFalse ClassUtils.isPrimitveAndString(Status)

	assertTrue ClassUtils.isPrimitveAndString(Integer)
	assertTrue ClassUtils.isPrimitveAndString(String)

    }

    @Test
    public void testGetFieldGenertics() {
	def expectedException = shouldFail(IllegalArgumentException){ ClassUtils.getFieldGenertics(null) }

	assertEquals 'field is null.', expectedException.message

	// テストデータ初期化
	Field stringField = RwsItemSearchResult.getDeclaredField('code')
	Field listField = RwsItemSearchResult.getDeclaredField('items')

	assertEquals String, ClassUtils.getFieldGenertics(stringField)
	assertEquals RwsResponseItem, ClassUtils.getFieldGenertics(listField)

	listField = TestClz.getDeclaredField('testlist')
	assertEquals List, ClassUtils.getFieldGenertics(listField)
    }

    private class TestClz{
	List<? > testlist
    }
}
