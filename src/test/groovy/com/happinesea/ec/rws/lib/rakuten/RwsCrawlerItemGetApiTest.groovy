package com.happinesea.ec.rws.lib.rakuten

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

import groovyx.net.http.ContentType

class RwsCrawlerItemGetApiTest {
    RwsCrawlerItemGetApi proxy
    @Before
    void before() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML

	proxy = new RwsCrawlerItemGetApi(header)
    }

    @Test
    public void testInitClass() {
	assertEquals '/es/1.0/item/get', proxy.path
	assertEquals 'https://api.rms.rakuten.co.jp', proxy.requestUri
	assertNotNull proxy.rwsResponseParser
	assertTrue proxy.rwsResponseParser instanceof RwsResponseXmlParser
	assertEquals HttpMethod.GET, proxy.httpMethod
    }
}
