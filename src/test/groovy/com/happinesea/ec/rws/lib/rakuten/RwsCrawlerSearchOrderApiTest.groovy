package com.happinesea.ec.rws.lib.rakuten

import static org.junit.Assert.*

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.BasicHttpEntity
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsCrawler
import com.happinesea.ec.rws.lib.RwsResponseJsonParser
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsRakutenPayOrderAPISearchOrderForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsSearchOrderResult

import groovyx.net.http.ContentType

class RwsCrawlerSearchOrderApiTest {

    RwsCrawlerSearchOrderApi rwsCrawlerSearchOrderApi

    @Before
    void before() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.JSON

	rwsCrawlerSearchOrderApi = new RwsCrawlerSearchOrderApi(header)
    }
    @Test
    public void testInitClass() {
	assertEquals '/es/2.0/order/searchOrder/', rwsCrawlerSearchOrderApi.path
	assertEquals 'https://api.rms.rakuten.co.jp', rwsCrawlerSearchOrderApi.requestUri
	assertNotNull rwsCrawlerSearchOrderApi.rwsResponseParser
	assertTrue rwsCrawlerSearchOrderApi.rwsResponseParser instanceof RwsResponseJsonParser
	assertEquals HttpMethod.JSON_POST, rwsCrawlerSearchOrderApi.httpMethod
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
    public void testRwsCrawlerSearchOrderApi() {
	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(searchOrderSuccess.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpPost httpPost -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	rwsCrawlerSearchOrderApi.crawler = crawlerMock
	rwsCrawlerSearchOrderApi.httpMethod = HttpMethod.JSON_POST

	RwsSearchOrderResult result = rwsCrawlerSearchOrderApi.run(new RwsRakutenPayOrderAPISearchOrderForm())
	assertNotNull result
	assertNotNull result.orderNumberList
	assertEquals '289999-20171117-00065901', result.orderNumberList[0]
	assertEquals '289999-20171117-00064901', result.orderNumberList[1]
	assertNotNull result.PaginationResponseModel
	assertEquals 79, result.PaginationResponseModel.totalRecordsAmount
	assertEquals 3, result.PaginationResponseModel.totalPages
	assertEquals 1, result.PaginationResponseModel.requestPage
	assertNotNull result.MessageModelList
	assertEquals 1, result.MessageModelList.size()
	assertEquals "INFO", result.MessageModelList[0].messageType
	assertEquals "ORDER_EXT_API_SEARCH_ORDER_INFO_101", result.MessageModelList[0].messageCode
	assertEquals "注文検索に成功しました。", result.MessageModelList[0].message
    }
}
