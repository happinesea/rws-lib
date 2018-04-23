package com.happinesea.ec.rws.lib.rakuten

import static org.junit.Assert.*

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.BasicHttpEntity
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.lib.RwsCrawler
import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.AbstractApiProxy.HttpMethod
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategoriesGetResponseResult

import groovyx.net.http.ContentType

class CategoryapiShopCategoriesGetTest {
    CategoryapiShopCategoriesGet proxy

    @Before
    void before() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.serviceSecret = ''
	header.licenseKey = ''
	header.acceptCharset = 'utf-8'
	header.contentType = ContentType.XML

	proxy = new CategoryapiShopCategoriesGet(header)
    }

    @Test
    public void testInitClass() {

	assertEquals '/es/1.0/categoryapi/shop/category/get', proxy.path
	assertEquals 'https://api.rms.rakuten.co.jp', proxy.requestUri
	assertNotNull proxy.rwsResponseParser
	assertTrue proxy.rwsResponseParser instanceof RwsResponseXmlParser
	assertEquals HttpMethod.GET, proxy.httpMethod
    }


    def categoriesGetResponseResult = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>shop.categories.get</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f55</requestId>
		  
		<!-- To understand the request from client. But, do not record the authentication information.-->
		<requests>
			<categorySetManageNumber>100</categorySetManageNumber>
		</requests>
	</status>
	<categoriesGetResult>
		<code>N000</code>
                <shopId/>
		<categorySetManageNumber>1</categorySetManageNumber>
		<categoryList>
			<category>
				<categoryId>117</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>チョコレート</name>
				<status>0</status>
				<categoryWeight>1</categoryWeight>
				<childCategories/>
			</category>
			<category>
				<categoryId>118</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>ケーキ</name>
				<status>0</status>
				<categoryWeight>2</categoryWeight>
				<childCategories/>
			</category>
			<category>
				<categoryId>119</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>キャンディー</name>
				<status>0</status>
				<categoryWeight>3</categoryWeight>
				<childCategories>
					<category>
                				<categoryId>1191</categoryId>
                				<categoryLevel>2</categoryLevel>
                				<name>キャンディー-1</name>
                				<status>0</status>
                				<categoryWeight>3</categoryWeight>
                				<childCategories>
                					<category>
                                				<categoryId>11911</categoryId>
                                				<categoryLevel>3</categoryLevel>
                                				<name>キャンディー-1-1</name>
                                				<status>0</status>
                                				<categoryWeight>3</categoryWeight>
                                				<childCategories>
                                					<category>
                                                				<categoryId>119111</categoryId>
                                                				<categoryLevel>4</categoryLevel>
                                                				<name>キャンディー-1-1-1</name>
                                                				<status>0</status>
                                                				<categoryWeight>3</categoryWeight>
                                                				<childCategories>
                                                					
                                                				</childCategories>
                                                			</category>
                                					
                                				</childCategories>
                                			</category>
                				</childCategories>
                			</category>
					<category>
                				<categoryId>1192</categoryId>
                				<categoryLevel>2</categoryLevel>
                				<name>キャンディー-2</name>
                				<status>0</status>
                				<categoryWeight>3</categoryWeight>
                				<childCategories>
                					<category>
                                				<categoryId>11954</categoryId>
                                				<categoryLevel>3</categoryLevel>
                                				<name>キャンディー-2-1</name>
                                				<status>0</status>
                                				<categoryWeight>3</categoryWeight>
                                				<childCategories>
                                					
                                				</childCategories>
                                			</category>
                				</childCategories>
                			</category>
				</childCategories>
			</category>
		</categoryList>
	</categoriesGetResult>
</result>'''

    @Test
    public void testExcute() {


	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(categoriesGetResponseResult.getBytes('UTF-8')))

	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpGet httpGet -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	proxy.crawler = crawlerMock

	RwsCategoriesGetResponseResult result = proxy.run(new RwsBaseForm())
	assertNotNull result
	assertNotNull result.status
	assertNotNull result.categoriesGetResult
	assertEquals 'shop.categories.get', result.status.interfaceId
	assertEquals 117, result.categoriesGetResult.categoryList[0].categoryId
    }
}
