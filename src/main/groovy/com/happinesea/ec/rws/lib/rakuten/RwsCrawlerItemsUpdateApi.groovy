package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.AbstractApiProxy.HttpMethod

/**
 * 
 *
 */
class RwsCrawlerItemsUpdateApi<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    RwsCrawlerItemsUpdateApi(){
	super('/es/1.0/items/update')
	httpMethod = HttpMethod.POST
    }
}
