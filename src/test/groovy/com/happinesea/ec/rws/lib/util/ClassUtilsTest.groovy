package com.happinesea.ec.rws.lib.util
import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsResponseParser
import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseJsonResult.MessageModel
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult.Status
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.SystemStatusElementEnum
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItem
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemGetResponseResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemGetResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemSearchResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsSearchOrderResult
import com.happinesea.ec.rws.lib.rakuten.RwsCrawlerCategoryapiShopCategoriesGetApi

import groovy.beans.Bindable

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
    public void testGetFieldsApiResponse_forJsonReuslt() {
	Field[] fields = ClassUtils.getFieldsApiResponse(RwsSearchOrderResult)

	assertEquals 3, fields.length
	assertEquals 'orderNumberList', fields[0].getName()
	assertEquals 'PaginationResponseModel', fields[1].getName()
	assertEquals 'MessageModelList', fields[2].getName()

	def result = new RwsSearchOrderResult()
	List<MessageModel> msgList = new ArrayList<MessageModel>()
	MessageModel msg = new MessageModel()
	msg.messageCode = 'test'
	msgList.add(e: msg)
	result['MessageModelList'] = msgList

	assertNotNull result.MessageModelList
	assertEquals msgList, result.MessageModelList
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
	assertFalse ClassUtils.isPrimitve(null)

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
	Field stringListField = RwsSearchOrderResult.getDeclaredField('orderNumberList')

	assertEquals String, ClassUtils.getFieldGenertics(stringField)
	assertEquals RwsItem, ClassUtils.getFieldGenertics(listField)
	assertEquals String, ClassUtils.getFieldGenertics(stringListField)

	listField = TestClz.getDeclaredField('testlist')
	assertEquals List, ClassUtils.getFieldGenertics(listField)
    }

    private class TestClz{
	List<? > testlist
    }

    @Test
    public void testGetClassesByGenericSignature() {
	assertNull ClassUtils.getClassesByGenericSignature(null)
	assertNull ClassUtils.getClassesByGenericSignature(TestClz)

	Class[] result = ClassUtils.getClassesByGenericSignature(RwsCrawlerCategoryapiShopCategoriesGetApi)
	assertNotNull result

	String typeName = '<RwsBaseForm:Ljava/lang/Object;RwsCategorysetsGetResponseResult:Ljava/lang/Object;>Lcom/happinesea/ec/rws/lib/AbstractApiProxy;'

    }

    @Test
    public void testGetBeanClassByName() throws Exception{
	def expectedException = shouldFail(IllegalArgumentException){ ClassUtils.getBeanClassByName(null) }
	assertEquals 'class name is empty.', expectedException.message

	assertNull ClassUtils.getBeanClassByName('hoge', 'hoge')

	Class rwsBaseForm = ClassUtils.getBeanClassByName(null, 'RwsBaseForm')
	assertNotNull rwsBaseForm
	assertEquals RwsBaseForm, rwsBaseForm

	Class status = ClassUtils.getBeanClassByName('Status')
	assertNotNull status
	assertEquals Status, status

	Class bindable = ClassUtils.getBeanClassByName('groovy/beans', 'Bindable')
	assertNotNull bindable
	assertEquals Bindable, bindable

    }

    @Test
    public void testGetFieldApiResponse() {
	assertNull ClassUtils.getFieldApiResponse(null, null)
	assertNull ClassUtils.getFieldApiResponse(RwsSearchOrderResult, null)
	assertNull ClassUtils.getFieldApiResponse(null, 'orderNumberList')
	assertNull ClassUtils.getFieldApiResponse(RwsSearchOrderResult, 'hoge')
	assertNotNull ClassUtils.getFieldApiResponse(RwsSearchOrderResult, 'orderNumberList')
	Field f1 = ClassUtils.getFieldApiResponse(RwsSearchOrderResult, 'orderNumberList')
	assertEquals 'orderNumberList', f1.getName()
	assertNotNull ClassUtils.getFieldApiResponse(RwsSearchOrderResult, 'MessageModelList')
	Field f2 = ClassUtils.getFieldApiResponse(RwsSearchOrderResult, 'MessageModelList')
	assertEquals 'MessageModelList', f2.getName()

    }
}
