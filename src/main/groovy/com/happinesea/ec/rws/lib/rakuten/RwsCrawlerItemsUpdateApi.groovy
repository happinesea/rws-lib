package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 * 
 *
 */
class RwsCrawlerItemsUpdateApi<RwsItemApiUpdateItemListForm, RwsItemsUpdateResult> extends AbstractApiProxy {
    RwsCrawlerItemsUpdateApi(RwsRequestHeaderBean header){
	super('/es/1.0/items/update', header)
	httpMethod = HttpMethod.XML_POST
    }
}
