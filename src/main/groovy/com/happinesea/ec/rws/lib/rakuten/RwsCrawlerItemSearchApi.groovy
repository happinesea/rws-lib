package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.bean.rakuten.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 *  * 「RMS WEB SERVICE : item.search」の通信処理を行うAPIクラス<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/search
 *
 */
class RwsCrawlerItemSearchApi<RwsItemApiSearchForm, RwsItemSearchResponseResult> extends AbstractApiProxy {
    /**
     * URLと通信メソッドを初期化して、インスタンスを生成する
     * 
     * @param header 通信ヘッダー情報
     */
    RwsCrawlerItemSearchApi(RwsRequestHeaderBean header){
	super('/es/1.0/item/search', header)
	httpMethod = HttpMethod.GET
    }
}
