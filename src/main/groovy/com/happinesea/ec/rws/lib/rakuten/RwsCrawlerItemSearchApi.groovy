package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.AbstractApiProxy.HttpMethod

/**
 * 
 *
 */
class RwsCrawlerItemSearchApi<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    RwsCrawlerItemSearchApi(){
	super('/es/1.0/item/search')
	httpMethod = HttpMethod.GET
    }
}
