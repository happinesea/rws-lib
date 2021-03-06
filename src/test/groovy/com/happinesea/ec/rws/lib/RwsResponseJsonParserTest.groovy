package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.commons.collections.CollectionUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseJsonResult

class RwsResponseJsonParserTest {

    @Test
    public void testParse_nullcheck() {
	RwsResponseJsonParser parser = new RwsResponseJsonParser()

	def expectedException = shouldFail(IllegalArgumentException){parser.parse((String)null , String)}
	assertEquals 'Content or clz is null.', expectedException.message
	expectedException = shouldFail(IllegalArgumentException){parser.parse('json' , null)}
	assertEquals 'Content or clz is null.', expectedException.message
	expectedException = shouldFail(RuntimeException){parser.parse('json', RwsResponseJsonResult)}

	assertNull parser.parse((Map)null, Class)

	assertNotNull parser.parse(new HashMap(), Dummy)
	assertTrue (parser.parse(new HashMap(), Dummy) instanceof Dummy)
    }
    def searchOrderSuccess = '''{
    "orderNumberList": ["289999-20171117-00065901","289999-20171117-00064901"],
    "MessageModelList": [
        {
            "messageType": "INFO",
            "messageCode": "ORDER_EXT_API_SEARCH_ORDER_INFO_101",
            "message": "注文検索に成功しました。"
        }
    ],
    "PaginationResponseModel": {
        "totalRecordsAmount": 79,
        "totalPages": 3,
        "requestPage": 1
    }
}'''
    @Test
    public void testParseStringClassOfR() {
	RwsResponseJsonParser parser = new RwsResponseJsonParser()
	RwsResponseJsonResult result = parser.parse(searchOrderSuccess, RwsResponseJsonResult)

	assertNotNull result
	assertTrue CollectionUtils.isNotEmpty(result.MessageModelList)
	assertNotNull result.MessageModelList[0]
	assertEquals '注文検索に成功しました。', result.MessageModelList[0].message
	assertEquals 'ORDER_EXT_API_SEARCH_ORDER_INFO_101', result.MessageModelList[0].messageCode
	assertEquals 'INFO', result.MessageModelList[0].messageType
    }
}
