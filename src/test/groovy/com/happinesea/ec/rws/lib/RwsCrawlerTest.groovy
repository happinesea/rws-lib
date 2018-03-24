package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.apache.http.Header
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsParameter
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.request.form.RwsItemApiSearchForm

import groovyx.net.http.ContentType

class RwsCrawlerTest {

    RwsCrawler crawler

    @Before
    void before() {
	crawler = new RwsCrawler()
	assertEquals(crawler, crawler.init())
    }

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



	RwsParameter<RwsItemApiSearchForm> paramter = new RwsParameter<RwsItemApiSearchForm>()
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


	//TODO crawler.getApiRequest(new HTTPBuilder(), paramter)
    }
}
