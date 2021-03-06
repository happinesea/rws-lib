package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.collections.CollectionUtils
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.BasicHttpEntity
import org.junit.Before
import org.junit.Test

import com.happinesea.ec.rws.cs.bean.form.MediaPostForm
import com.happinesea.ec.rws.cs.bean.form.WpPostForm
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.enumerated.ApiTypeEnum
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiSearchForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsRakutenPayOrderAPISearchOrderForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemSearchResponseResult

import groovy.util.logging.Log4j2
import groovyx.net.http.ContentType

@Log4j2
class RwsCrawlerTest {

    RwsCrawler crawler
    RwsParameter<RwsItemApiSearchForm> rwsItemApiSearchparamter
    RwsParameter<RwsRakutenPayOrderAPISearchOrderForm> rwsRakutenPayOrderAPISearchOrderparamter

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

	rwsRakutenPayOrderAPISearchOrderparamter = new RwsParameter<RwsRakutenPayOrderAPISearchOrderForm>()
	BeanUtils.copyProperties(rwsRakutenPayOrderAPISearchOrderparamter, rwsItemApiSearchparamter)
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
    public void testPostJsonRequest_searchOrder() {
	BasicHttpEntity entity = new BasicHttpEntity()
	entity.setContent(new ByteArrayInputStream(searchOrderSuccess.getBytes('UTF-8')))


	def httpResponseMock = [getEntity:{return entity}] as HttpResponse
	def httpClientMock = [execute:{HttpPost httpPost -> return httpResponseMock}] as HttpClient

	def crawlerMock = [init:{RwsParameter parameter -> return httpClientMock}] as RwsCrawler

	def result = crawlerMock.postJsonRequest(rwsRakutenPayOrderAPISearchOrderparamter)
    }

    @Test
    public void testPostRestRequest() {
	RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	header.apiType = ApiTypeEnum.BASIC
	header.userName = 'jc-writer@happinesea.com'
	header.password = '3GrF nTA2 9JNP 51zN jzni rqCo' // 2MC445sH

	rwsItemApiSearchparamter.header = header
	rwsItemApiSearchparamter.requestUri = 'http://test2.happinesea.com'
	//rwsItemApiSearchparamter.path = '/wp-json/wp/v2/posts'
	rwsItemApiSearchparamter.path = '/wp-json/wp/v2/media'
	//rwsItemApiSearchparamter.path = '/info.php'
	//rwsItemApiSearchparamter.httpMethod = HttpMethod.PO

	WpPostForm form = new WpPostForm()
	form.title = "测试用标题"
	form.content = "<h1>测试内容</h2>"
	form.status = "publish"
	form.categories = new ArrayList()
	form.categories.add(10)


	rwsItemApiSearchparamter.requestForm = form

	HttpResponse response = crawler.postRestRequest(rwsItemApiSearchparamter)
	HttpEntity entity =response.entity;
	println response

	BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
	StringBuilder sb = new StringBuilder()
	String line
	while ((line = br.readLine()) != null) {
	    sb.append(line);
	}
	println sb

    }

    public static void main(String[] arg) throws Exception {
	URL url = new URL("http://n.sinaimg.cn/news/transform/61/w550h311/20180917/WanF-hkhfqns0811885.jpg")


	HttpURLConnection conn =
		(HttpURLConnection) url.openConnection();
	conn.setAllowUserInteraction(false);
	conn.setInstanceFollowRedirects(true);
	conn.setRequestMethod("GET");
	conn.connect();

	InputStream is = new DataInputStream(conn.getInputStream())

	println URLConnection.guessContentTypeFromStream(is)
	MediaPostForm form = null
	println form?.title
	form = new MediaPostForm()
	println form?.title
	form.title = "title"
	println form?.title

	println url.openConnection().getContentType()


    }
}