package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

/**
 * 
 *
 */
class RwsCrawlerItemSearch<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    RwsCrawlerItemSearch(){
	super('/es/1.0/')//TODO
	rwsResponseParser = new RwsResponseXmlParser()
    }

    public RwsResponseXmlResult excute(RwsParameter parameter) {
	if(rwsResponseParser == null) {
	    return null
	}
	//return crawler.getApiContents(parameter, rwsResponseParser, RwsItemSearchResponseResult);
	return null;
    }
}
