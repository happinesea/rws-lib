package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.RwsResponseJsonParser
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 * *　「RMS WEB SERVICE : searchOrder」の通信処理を行うAPIクラス<br>
 * https://api.rms.rakuten.co.jp/es/2.0/order/searchOrder/
 *
 */
class RwsCrawlerSearchOrderApi<RwsRakutenPayOrderAPISearchOrderForm, RwsSearchOrderResult> extends AbstractApiProxy {
    /**
     * URLと通信メソッドを初期化して、インスタンスを生成する
     * 
     * @param header 通信ヘッダー情報
     */
    RwsCrawlerSearchOrderApi(RwsRequestHeaderBean header){
	super('/es/2.0/order/searchOrder/', header)
	httpMethod = HttpMethod.JSON_POST
	rwsResponseParser = new RwsResponseJsonParser()
    }
}
