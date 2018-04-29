package com.happinesea.ec.rws.lib.rakuten

import static org.junit.Assert.*

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.BasicHttpEntity
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsCrawler
import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiUpdateItemListForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemsUpdateResult

import groovyx.net.http.ContentType

/**
 *
 */
class RwsCrawlerItemsUpdateApiTest {
    RwsCrawlerItemsUpdateApi proxy
    @Before
    void before() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML

	proxy = new RwsCrawlerItemsUpdateApi(header)
    }

    @Test
    public void testInitClass() {
	assertEquals '/es/1.0/items/update', proxy.path
	assertEquals 'https://api.rms.rakuten.co.jp', proxy.requestUri
	assertNotNull proxy.rwsResponseParser
	assertTrue proxy.rwsResponseParser instanceof RwsResponseXmlParser
	assertEquals HttpMethod.XML_POST, proxy.httpMethod
    }

    def itemUpdateItemsUpdateResult = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
    <status>
        <interfaceId>items.update</interfaceId>
        <systemStatus>OK</systemStatus>
        <message>OK</message>
        <requestId>3106e794-0631-4a3d-899c-97a473b017de</requestId>
        <requests/>
    </status>
    <itemsUpdateResult>
        <itemUpdateResult>
            <code>N000</code>
            <item>
                <itemUrl>itemXXX</itemUrl>
            </item>
        </itemUpdateResult>
        <itemUpdateResult>
            <code>C219</code>
            <errorMessages>
                <errorMessage>
                    <errorId>1222</errorId>
                    <fieldId>540</fieldId>
                    <msg>カタログIDもしくはカタログIDなしの理由を入力してください。</msg>
                </errorMessage>
            </errorMessages>
            <item>
                <itemUrl>itemYYY</itemUrl>
            </item>
        </itemUpdateResult>
     </itemsUpdateResult>
</result>'''
    /**
     * Test method for {@link com.happinesea.ec.rws.lib.rakuten.RwsCrawlerItemsUpdateApi#RwsCrawlerItemsUpdateApi()}.
     */
    @Test
    public void testRwsCrawlerItemsUpdateApi() {
	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(itemUpdateItemsUpdateResult.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpPost httpPost -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	proxy.crawler = crawlerMock

	RwsItemsUpdateResult result = proxy.run(new RwsItemApiUpdateItemListForm())
	assertNotNull result
	assertNotNull result.status
	assertNotNull result.itemsUpdateResult
	assertEquals 'items.update', result.status.interfaceId
	assertEquals 'N000', result.itemsUpdateResult[0].code
    }
}
