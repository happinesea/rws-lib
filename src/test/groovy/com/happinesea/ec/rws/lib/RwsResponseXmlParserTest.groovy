package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsItemResponseResult

import groovy.util.logging.Log4j2

@Log4j2
class RwsResponseXmlParserTest {
    def packagePath = 'com/happinesea/ec/rws/lib/'

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
				<itemUrl>aaa</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>aaa</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>aaa</itemUrl>
				<!-- omission -->
			</item>
		</items>
	</itemSearchResult>
</result>'''

    @Test
    public void testParseItemSearch() {

	RwsResponseXmlParser<RwsItemResponseResult> parser = new RwsResponseXmlParser<RwsItemResponseResult>();

	RwsItemResponseResult result = parser.parse(itemSearchXmlSuccess, RwsItemResponseResult.class)

	assertNotNull result
	assertNotNull result.status
	//assertNotNull result.status.interfaceId
	log.debug(result.status.interfaceId)
    }
}
