package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 *　「RMS WEB SERVICE : shop.categories.get」の通信処理を行うAPIクラス<br>
 *https://api.rms.rakuten.co.jp/es/1.0/categoryapi/shop/categories/get
 *
 */
class RwsCrawlerCategoryapiShopCategoriesGetApi<RwsBaseForm, RwsCategoriesGetResponseResult> extends AbstractApiProxy {
    /**
     * URLと通信メソッドを初期化して、インスタンスを生成する
     * 
     * @param header 通信ヘッダー情報
     */
    public RwsCrawlerCategoryapiShopCategoriesGetApi(RwsRequestHeaderBean header){
	super('/es/1.0/categoryapi/shop/categories/get', header)
	httpMethod = HttpMethod.GET
    }
}
