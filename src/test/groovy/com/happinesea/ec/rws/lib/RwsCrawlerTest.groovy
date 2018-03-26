package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.http.Header
import org.apache.http.client.HttpClient
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsParameter
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.form.RwsItemApiSearchForm
import com.happinesea.ec.rws.lib.bean.node.RwsItemSearchResponseResult

import groovy.mock.interceptor.MockFor
import groovyx.net.http.ContentType

class RwsCrawlerTest {

    @Test
    public void testGetRequestHeaderStr() {
	RwsCrawler crawler = new RwsCrawler()
	List<Header> result = crawler.getRequestHeaderStr(null)

	assertNotNull result
	assertTrue result.isEmpty()

	RwsRequestHeaderBean bean = new RwsRequestHeaderBean()
	assertTrue crawler.getRequestHeaderStr(bean).isEmpty()

	bean.serviceSecret = 'x'
	bean.licenseKey = 'y'
	result = crawler.getRequestHeaderStr(bean)
	assertEquals 1, result.size()
	assertEquals 'ESA ' + 'x:y'.bytes.encodeBase64().toString(), result[0].getValue()

	bean.contentType = ContentType.JSON
	result = crawler.getRequestHeaderStr(bean)
	assertEquals 2, result.size()
	assertEquals 'ESA ' + 'x:y'.bytes.encodeBase64().toString(), result[0].getValue()
	assertEquals ContentType.JSON.toString() , result[1].getValue()

	bean.acceptCharset = 'z'
	result = crawler.getRequestHeaderStr(bean)
	assertEquals 3, result.size()
	assertEquals 'ESA ' + 'x:y'.bytes.encodeBase64().toString(), result[0].getValue()
	assertEquals ContentType.JSON.toString(), result[1].getValue()
	assertEquals 'z', result[2].getValue()
    }

    @Test
    public void testGetApiRequest() {
	RwsCrawler<RwsItemSearchResponseResult> crawler = new RwsCrawler<RwsItemSearchResponseResult>()

	def expectedException = shouldFail(IllegalArgumentException){crawler.getApiRequest(null)}
	assertEquals 'invalide request info.', expectedException.message


	RwsParameter<RwsItemApiSearchForm> paramter = new RwsParameter<RwsItemApiSearchForm>()

	expectedException = shouldFail(IllegalArgumentException){crawler.getApiRequest(paramter)}
	assertEquals 'invalide request info.', expectedException.message

	paramter.path = '/es/1.0/item/search'
	paramter.requestUri = 'https://api.rms.rakuten.co.jp'
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML
	paramter.header = header
	paramter.requestForm = new RwsItemApiSearchForm()

	paramter.requestForm.itemName = '馬油'

	def httpClientMock = new MockFor(HttpClient)
	httpClientMock.demand.execute{ return null }


	/*httpClientMock.use {
	 HttpResponse response = crawler.getApiRequest(paramter)
	 assertNull response
	 }*/
    }


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
}