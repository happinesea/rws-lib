package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.RwsResponseXmlParser

/**
 * 
 *
 */
class CategoryapiShopCategoriesGet<RwsParameter, RwsCategoriesGetResult> extends AbstractApiProxy {

    @Override
    public RwsCategoriesGetResult excute(RwsParameter parameter) {
	if(rwsResponseParser == null) {
	    return null
	}
	return crawler.getApiContents(parameter, new RwsResponseXmlParser(), RwsCategoriesGetResult);
    }
}
