package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.commons.collections.CollectionUtils
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.BasicHttpEntity
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiSearchForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemSearchResponseResult

import groovy.util.logging.Log4j2
import groovyx.net.http.ContentType

@Log4j2
class RwsCrawlerTest {

    RwsCrawler crawler
    RwsParameter<RwsItemApiSearchForm> rwsItemApiSearchparamter

    @Before
    void before() {
	crawler = new RwsCrawler()
	rwsItemApiSearchparamter = new RwsParameter<RwsItemApiSearchForm>()
	rwsItemApiSearchparamter.path = '/es/1.0/item/search'
	rwsItemApiSearchparamter.requestUri = 'https://api.rms.rakuten.co.jp'
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML
	rwsItemApiSearchparamter.header = header
	rwsItemApiSearchparamter.requestForm = new RwsItemApiSearchForm()
	rwsItemApiSearchparamter.requestForm.itemName = '馬油'
    }

    @Test
    public void testInit() {
	HttpClient client = crawler.init(rwsItemApiSearchparamter)

	assertNotNull client
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
			</item>
			<item>
				<itemUrl>a2</itemUrl>
			</item>
			<item>
				<itemUrl>a3</itemUrl>
			</item>
		</items>
	</itemSearchResult>
</result>'''

    @Test
    public void testGetRequestHeaderStr() {
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
	def expectedException = shouldFail(IllegalArgumentException){crawler.getApiRequest(null)}
	assertEquals 'invalide request info.', expectedException.message



	expectedException = shouldFail(IllegalArgumentException){crawler.getApiRequest(new RwsParameter())}
	assertEquals 'invalide request info.', expectedException.message


	HttpEntity entity = new BasicHttpEntity()
	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpGet httpGet -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	assertEquals httpResponseMock, crawlerMock.getApiRequest(rwsItemApiSearchparamter)
    }

    @Test
    public void testGetApiContents_parameterError() {
	def expectedException = shouldFail(IllegalArgumentException){crawler.getApiContents(null, null, null)}
	assertEquals 'invalide parser.', expectedException.message

	expectedException = shouldFail(IllegalArgumentException){crawler.getApiContents(null, new RwsResponseXmlParser(), null)}
	assertEquals 'invalide header info.', expectedException.message


	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(''.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpGet httpGet -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	def result = crawlerMock.getApiContents(rwsItemApiSearchparamter, new RwsResponseXmlParser(), null)

	assertNull result
    }
    @Test
    public void testGetApiContents_bacsic() {

	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(itemSearchXmlSuccess.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpGet httpGet -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	def result = crawlerMock.getApiContents(rwsItemApiSearchparamter, new RwsResponseXmlParser(), null)

	assertTrue result instanceof RwsResponseXmlResult
	assertNotNull result.status
	assertEquals 'item.search', result.status.interfaceId
    }
    @Test
    public void testGetApiContents_itemSearch() {

	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(itemSearchXmlSuccess.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpGet httpGet -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	def result = crawlerMock.getApiContents(rwsItemApiSearchparamter, new RwsResponseXmlParser(), RwsItemSearchResponseResult)
	assertTrue result instanceof RwsItemSearchResponseResult
	assertNotNull result.status
	assertEquals 'item.search', result.status.interfaceId
	assertNotNull result.itemSearchResult
	assertEquals '200-00', result.itemSearchResult.code
	assertEquals 10, result.itemSearchResult.numFound
	assertTrue CollectionUtils.isNotEmpty(result.itemSearchResult.items)
	assertEquals 3, result.itemSearchResult.items.size()
	assertNotNull result.itemSearchResult.items[0]
	assertEquals 'a1', result.itemSearchResult.items[0].itemUrl
	assertEquals 'a2', result.itemSearchResult.items[1].itemUrl
	assertEquals 'a3', result.itemSearchResult.items[2].itemUrl
    }
}
