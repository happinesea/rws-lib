package com.happinesea.ec.rws.lib.rakuten

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

import groovyx.net.http.ContentType

/**
 *
 */
class RwsCrawlerItemsSearchApiTest {
    RwsCrawlerItemSearchApi proxy
    @Before
    void before() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML

	proxy = new RwsCrawlerItemSearchApi(header)
    }

    @Test
    public void testInitClass() {
	assertEquals '/es/1.0/item/search', proxy.path
	assertEquals 'https://api.rms.rakuten.co.jp', proxy.requestUri
	assertNotNull proxy.rwsResponseParser
	assertTrue proxy.rwsResponseParser instanceof RwsResponseXmlParser
	assertEquals HttpMethod.GET, proxy.httpMethod
    }

    /**
     * Test method for {@link com.happinesea.ec.rws.lib.rakuten.RwsCrawlerItemsUpdateApi#RwsCrawlerItemsUpdateApi()}.
     */
    @Test
    public void testRwsCrawlerItemsUpdateApi() {
	//fail("Not yet implemented")
    }
}
