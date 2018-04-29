package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 * 
 *
 */
class RwsCrawlerItemSearchApi<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    RwsCrawlerItemSearchApi(RwsRequestHeaderBean header){
	super('/es/1.0/item/search', header)
	httpMethod = HttpMethod.GET
    }
}
