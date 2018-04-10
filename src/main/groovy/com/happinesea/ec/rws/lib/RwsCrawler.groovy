package com.happinesea.ec.rws.lib

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import org.apache.commons.lang.StringUtils
import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicHeader

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

import groovy.util.logging.Log4j2

/**
 * RWSクローラー
 * 
 * 
 * 
 *
 */
@Log4j2
class RwsCrawler {
    /**
     * 設定情報
     */
    private HappineseaConfig config

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
     * 
     * @param parameter
     * @param parser
     * @param clz
     * @return
     */
    public <R extends RwsResponseXmlResult>R getApiContents(RwsParameter parameter, RwsResponseParser parser, Class<R> clz) {
	if(parser == null) {
	    throw new IllegalArgumentException('invalide parser.')
	}
	HttpEntity entity = getApiRequest(parameter).entity
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

	return httpClient.execute(httpGet);
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
}
