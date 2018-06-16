package com.happinesea.ec.rws.lib

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod
import com.happinesea.ec.rws.lib.util.ClassUtils

import groovy.util.logging.Log4j2

/**
 * API通信を行うプロキシ
 */
@Log4j2
abstract class AbstractApiProxy<F extends RwsBaseForm, R extends RwsResponseXmlResult> {

    /**
     * スキーマからホストまでのURI
     */
    String requestUri
    /**
     * APIのパス
     */
    String path
    /**
     * デフォルトエンコード
     */
    String defaultEncode
    /**
     * HTTPメソッド
     */
    HttpMethod httpMethod
    /**
     * ヘッダー情報
     */
    RwsRequestHeaderBean header
    /**
     * クローラとなるHTTPクライアントオブジェクト
     */
    RwsCrawler crawler
    /**
     * レスポンスデータをオブジェクトにパースするオブジェクト
     */
    RwsResponseParser rwsResponseParser
    /**
     * 設定情報
     */
    HappineseaConfig config = HappineseaConfig.getInstance()

    /**
     * APIのパス、ヘッダー情報を指定して、インスタンスを初期化
     * @param path APIのパス
     * @param header ヘッダー情報
     */
    AbstractApiProxy(String path, RwsRequestHeaderBean header) {
	 this.requestUri = config.rmsApiUrl
	 this.path = path
	 this.defaultEncode = config.defaultEncode
	 this.header = header
	 this.rwsResponseParser = new RwsResponseXmlParser()
     }

    /**
     * 実行するメソッド
     * 
     * @param parameter
     * @return
     */
    public R run(F form) throws IOException {

	// valdation check


	RwsParameter<F> parameter = new RwsParameter<F>()
	parameter.header = header
	parameter.requestUri = requestUri
	parameter.path = path
	if(httpMethod) {
	    parameter.httpMethod = httpMethod
	}
	parameter.requestForm = form
	
	crawler.init(parameter)
	
	Class[] types = ClassUtils.getClassesByGenericSignature(this.getClass())
	def result = null
	try {
	    result = crawler.getApiContents(parameter, rwsResponseParser, types[1])
	}catch(IOException e) {
	    throw e
	}
	
	log.info('R type : {}', this.getClass().getGenericSignature0())
	log.info('result type : {}', result.getClass().getName())
	return result
    }

 
}
