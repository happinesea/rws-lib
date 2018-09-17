package com.happinesea.ec.rws.lib

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import java.nio.charset.StandardCharsets

import org.apache.commons.lang.StringUtils
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicHeader

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

import groovy.util.logging.Log4j2

/**
 * RWSクローラーのHTTPクライアントクラス
 *
 */
@Log4j2
public class RwsCrawler {
    /**
     * 設定情報
     */
    HappineseaConfig config

    /**
     * コンストラクタ
     */
    public RwsCrawler() {
	config = HappineseaConfig.getInstance()
    }

    /**
     * クローラーの初期化を行う
     * 
     * @return 自分自身のインスタンスを戻す
     */
    protected HttpClient init(RwsParameter parameter) {

	// RequestConfig
	RequestConfig requestConfig = RequestConfig.custom()
		.setConnectTimeout(config.connectionTimeout)
		.setSocketTimeout(config.socketTimeout).build()

	// HttpClient
	return HttpClientBuilder.create()
		.setDefaultRequestConfig(requestConfig)
		.setDefaultHeaders(getRequestHeaderStr(parameter.header)).build();

    }

    /**
     * API通信して、取得した内容をパースされたオブジェクトとして取得する
     * 
     * @param parameter API通信パラメータ
     * @param parser パーサーインスタンス
     * @param clz パース結果のオブジェクト
     * @return
     */
    public <R extends ApiResponseNode>R getApiContents(RwsParameter parameter
	    , RwsResponseParser parser, Class<R> clz) throws IOException{
	if(parser == null) {
	    throw new IllegalArgumentException('invalide parser.')
	}
	if(parameter == null) {
	    throw new IllegalArgumentException('invalide header info.')
	}
	HttpEntity entity = null
	try {
	    if(parameter && parameter.httpMethod == HttpMethod.XML_POST){
		entity = postXmlRequest(parameter).entity
	    }else if(parameter && parameter.httpMethod == HttpMethod.JSON_POST) {
		entity = postJsonRequest(parameter).entity
	    }else {
		entity = getApiRequest(parameter).entity
	    }
	}catch(Exception e) {
	    throw new IOException(e)
	}
	if(clz == null) {
	    clz = RwsResponseXmlResult
	}

	BufferedReader br

	try {
	    br = new BufferedReader(new InputStreamReader(entity.getContent()));

	    StringBuilder sb = new StringBuilder()
	    String line
	    while ((line = br.readLine()) != null) {
		sb.append(line);
	    }
	    String contentStr = sb.toString()
	    if(log.isDebugEnabled()) {
		log.debug('contents str: {}', contentStr)
	    }
	    if(StringUtils.isEmpty(contentStr)) {
		return null
	    }
	    return parser.parse(contentStr, clz)
	}finally{
	    if(br != null) {
		br.close()
	    }
	}
    }

    /**
     * HTTP通信GETメソッドのレスポンスを返す
     * 
     * @param parameter リクエストパラメータ
     * @return レスポンス
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse getApiRequest(RwsParameter parameter) throws IOException, ClientProtocolException {
	if(parameter == null || parameter.getHeader() == null) {
	    throw new IllegalArgumentException('invalide request info.')
	}

	HttpClient httpClient = init(parameter)

	HttpGet httpGet = new HttpGet(parameter.getRequestUri() + parameter.getPath() + "?" + parameter.getQueryString());

	if(log.isDebugEnabled()) {
	    log.debug("http get: ${httpGet}")
	}

	return httpClient.execute(httpGet);
    }

    /**
     * HTTP通信POSTメソッド、送信bodyはXMLで送信したレスポンスを返す<br>
     * 
     * @param parameter
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse postXmlRequest(RwsParameter parameter) throws IOException, ClientProtocolException{

	HttpClient httpClient = init(parameter)

	HttpPost httpPost = new HttpPost(parameter.getRequestUri() + parameter.getPath());
	StringEntity entity = new StringEntity(parameter.getXmlString(), config.defaultEncode)
	httpPost.setEntity(entity)

	if(log.isDebugEnabled()) {
	    log.debug("request xml body({}) string ->{} ", entity.getContentLength(), parameter.getXmlString())
	}

	HttpResponse response = httpClient.execute(httpPost)
	return response
    }
    /**
     * RWS特化したHTTP通信用のヘッダー情報を作成する
     * 
     * @param bean ヘッダー情報を格納するbean
     * @return HTTPヘッダー情報
     */
    public List<Header> getRequestHeaderStr(RwsRequestHeaderBean bean) {

	List<Header> headers = new ArrayList<Header>()

	if(bean == null) {
	    return headers
	}

	if(bean.getAuthorization()) {
	    headers.add(new BasicHeader('Authorization', bean.getAuthorization()))
	}
	if(bean.getContentType()) {
	    headers.add(new BasicHeader('Content-Type', bean.getContentType().toString()))
	}
	if(bean.getAcceptCharset()) {
	    headers.add(new BasicHeader('Accept-Charset', bean.getAcceptCharset()))
	}

	return headers
    }


    /**
     * HTTP通信POSTメソッド、送信bodyはJSONで送信したレスポンスを返す<br>
     *
     * @param parameter
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse postJsonRequest(RwsParameter parameter) throws IOException, ClientProtocolException{

	HttpClient httpClient = init(parameter)

	HttpPost httpPost = new HttpPost(parameter.getRequestUri() + parameter.getPath());
	StringEntity entity = new StringEntity(parameter.getJsonString(), config.defaultEncode)
	httpPost.setEntity(entity)

	if(log.isDebugEnabled()) {
	    log.debug("request json body({}) string ->{} ", entity.getContentLength(), parameter.getJsonString())
	}

	HttpResponse response = httpClient.execute(httpPost)
	return response
    }

    /**
     * 
     * @param parameter
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse postRestRequest(RwsParameter parameter) throws IOException, ClientProtocolException{
	HttpClient httpClient = init(parameter)


	HttpPost httpPost = new HttpPost(parameter.getRequestUri() + parameter.getPath());
	//httpPost.addHeader("Content-Type", "multipart/form-data;charset=UTF-8")

	MultipartEntityBuilder builder = MultipartEntityBuilder.create()
	builder.setCharset(StandardCharsets.UTF_8);
	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


	httpPost.setEntity(parameter.getFormEntity(builder))

	HttpResponse response = httpClient.execute(httpPost)
	return response
    }
}
