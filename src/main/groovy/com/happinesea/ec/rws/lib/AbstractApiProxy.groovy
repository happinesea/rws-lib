package com.happinesea.ec.rws.lib

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

/**
 * API通信を行うプロキシ
 */
abstract class AbstractApiProxy<P extends RwsParameter, R extends RwsResponseXmlResult> {
    
    String requestUri
    String path
    String defaultEncode
    RwsCrawler crawler
    RwsResponseParser rwsResponseParser
    
    AbstractApiProxy(String requestUri, String path, String defaultEncode) {
	this.requestUri = requestUri
	this.path = path
	this.defaultEncode = defaultEncode
    }
    
    AbstractApiProxy(String requestUri, String path) {
	this(requestUri, path, HappineseaConfig.getInstance().defaultEncode)
    }
    
    /**
     * 実行するメソッド
     * 
     * @param parameter
     * @return
     */
    public R run(P parameter) {
	if(crawler == null) {
	    return null
	}
	
	crawler.init(parameter)
	
	R result = excute(parameter)
	
	return result
    }
    
    /**
     * 
     * @param parameter
     * @return
     */
    abstract R excute(P parameter)
}
