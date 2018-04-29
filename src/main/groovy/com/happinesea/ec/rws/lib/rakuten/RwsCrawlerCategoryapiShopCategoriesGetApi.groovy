package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.AbstractApiProxy.HttpMethod
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean

/**
 * 
 *
 */
class RwsCrawlerCategoryapiShopCategoriesGetApi<RwsBaseForm, RwsCategoriesGetResponseResult> extends AbstractApiProxy {
    public RwsCrawlerCategoryapiShopCategoriesGetApi(RwsRequestHeaderBean header){
	super('/es/1.0/categoryapi/shop/category/get', header)
	httpMethod = HttpMethod.GET
    }
}
