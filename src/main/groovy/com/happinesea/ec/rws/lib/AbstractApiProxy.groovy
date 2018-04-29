package com.happinesea.ec.rws.lib

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod
import com.happinesea.ec.rws.lib.util.ClassUtils

import groovy.util.logging.Log4j2

/**
 * API通信を行うプロキシ
 */
@Log4j2
abstract class AbstractApiProxy<F extends RwsBaseForm, R extends RwsResponseXmlResult> {

    String requestUri
    String path
    String defaultEncode
    HttpMethod httpMethod
    RwsRequestHeaderBean header
    RwsCrawler crawler
    RwsResponseParser rwsResponseParser
    HappineseaConfig config = HappineseaConfig.getInstance()

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
    public R run(F form) {

	// valdation check


	RwsParameter<F> parameter = new RwsParameter<F>()
	parameter.header = header
	if(httpMethod) {
	    parameter.httpMethod = httpMethod
	}
	
	crawler.init(parameter)
	
	Class[] types = ClassUtils.getClassesByGenericSignature(this.getClass())
	
	def result = crawler.getApiContents(parameter, rwsResponseParser, types[1])
	
	log.info('R type : {}', this.getClass().getGenericSignature0())
	log.info('result type : {}', result.getClass().getName())
	return result
    }

 
}
