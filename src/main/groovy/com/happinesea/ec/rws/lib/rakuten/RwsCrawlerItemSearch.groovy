package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.RwsResponseXmlParser

/**
 * 
 *
 */
class RwsCrawlerItemSearch<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    RwsCrawlerItemSearch(){
	super('/es/1.0/')//TODO
	rwsResponseParser = new RwsResponseXmlParser()
    }
}
