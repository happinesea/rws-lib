package com.happinesea.ec.rws.lib

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

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
import com.happinesea.ec.rws.lib.bean.RwsParameter
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.RwsResponseBody

import groovy.util.logging.Log4j2

/**
 * RWSクローラー
 * 
 * 
 * @author loveapple
 *
 */
@Log4j2
abstract class AbstractRwsCrawler {
    /**
     * 設定情報
     */
    private HappineseaConfig config

    /**
     * クローラーの初期化を行う
     * 
     * @return 自分自身のインスタンスを戻す
     */
    def init() {
	config = HappineseaConfig.getInstance()

	return this
    }

    /**
     * 
     * @param httpBuilder
     * @param parameter
     * @return
     */
    public <R extends RwsResponseBody> R getApiRequest(RwsParameter parameter, RwsResponseParser<R> parser) {
	if(parameter == null || parameter.getHeader() == null) {
	    throw new IllegalArgumentException('invalide request info.')
	}

	// RequestConfig
	RequestConfig requestConfig = RequestConfig.custom()
		.setConnectTimeout(config.connectionTimeout)
		.setSocketTimeout(config.socketTimeout).build()

	// HttpClient
	HttpClient httpClient = HttpClientBuilder.create()
		.setDefaultRequestConfig(requestConfig)
		.setDefaultHeaders(getRequestHeaderStr(parameter.header)).build();

	HttpGet httpGet = new HttpGet(parameter.getRequestUri() + parameter.getPath() + "?" + parameter.getQueryString());

	RwsResponseBody response = new R()
	try {
	    HttpResponse result= httpClient.execute(httpGet);

	    HttpEntity entity = result.entity

	    if(log.isDebugEnabled()) {
		log.debug('Request parameter: [{}]', parameter.getQueryString())
		log.debug('Response content: [{}]', entity.getContent().getText())
	    }

	    def rootNode = new XmlParser().parseText(entity.getContent())


	}catch(IOException e) {
	    response.thro = e;
	}catch(ClientProtocolException e) {
	    response.thro = e;

	}catch(Exception e) {
	    response.thro = e;

	}

	return response
    }

    /**
     * 
     * @param bean
     * @return
     */
    public List<Header> getRequestHeaderStr(RwsRequestHeaderBean bean) {

	List<Header> headers = new ArrayList<Header>()
	headers.add(new BasicHeader('Authorization', bean.getAuthorization()))
	headers.add(new BasicHeader('Content-Type', bean.getContentType().toString()))
	headers.add(new BasicHeader('Accept-Charset', bean.getAcceptCharset()))

	return headers
    }
}
