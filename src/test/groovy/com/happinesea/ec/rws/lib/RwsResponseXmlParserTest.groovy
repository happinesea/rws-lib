package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.node.RwsItemSearchResponseResult
import com.happinesea.ec.rws.lib.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

import groovy.util.logging.Log4j2

@Log4j2
class RwsResponseXmlParserTest {
    def itemSearchXmlSuccess = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>item.search</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f55</requestId>
		<requests>
			<itemUrl>aaa</itemUrl>
		</requests>
	</status>
	<itemSearchResult>
		<code>200-00</code>
		<numFound>
			10
		</numFound>
		<items>
			<item>
				<itemUrl>a1</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>a2</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>a3</itemUrl>
				<!-- omission -->
			</item>
		</items>
	</itemSearchResult>
</result>'''

    @Test
    public void testParseItemSearch() {

	RwsResponseXmlParser parser = new RwsResponseXmlParser()

	RwsItemSearchResponseResult result = parser.parse(itemSearchXmlSuccess, RwsItemSearchResponseResult)

	assertNotNull result
	assertNotNull result.status
	assertEquals 'item.search', result.status.interfaceId
	assertEquals SystemStatusElementEnum.OK, result.status.systemStatus
	assertEquals MessageElementEnum.OK, result.status.message
	assertEquals '714a4983-555f-42d9-aeea-89dae89f2f55', result.status.requestId
	// TODO requests 型定義から修正
	//assertEquals 'item.search', result.status.requests

	assertNotNull result.itemSearchResult
	assertEquals '200-00', result.itemSearchResult.code
	assertEquals 10, result.itemSearchResult.numFound

	assertNotNull result.itemSearchResult.items
	assertEquals 3, result.itemSearchResult.items.size()
	assertNotNull result.itemSearchResult.items[0]
	assertNotNull result.itemSearchResult.items[1]
	assertNotNull result.itemSearchResult.items[2]
	assertEquals 'a1', result.itemSearchResult.items[0].itemUrl
	assertEquals 'a2', result.itemSearchResult.items[1].itemUrl
	assertEquals 'a3', result.itemSearchResult.items[2].itemUrl
    }
}
