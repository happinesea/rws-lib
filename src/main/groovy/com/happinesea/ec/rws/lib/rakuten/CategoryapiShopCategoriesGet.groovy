package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.RwsResponseXmlParser
import com.happinesea.ec.rws.lib.AbstractApiProxy.HttpMethod
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean

/**
 * 
 *
 */
class CategoryapiShopCategoriesGet<RwsBaseForm, RwsCategoriesGetResponseResult> extends AbstractApiProxy {
    public CategoryapiShopCategoriesGet(RwsRequestHeaderBean header){
	super('/es/1.0/categoryapi/shop/category/get', header)
	rwsResponseParser = new RwsResponseXmlParser()
	httpMethod = HttpMethod.GET
    }
}
