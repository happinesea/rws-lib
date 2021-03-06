package com.happinesea.ec.rws.lib.rakuten

import com.happinesea.ec.rws.lib.AbstractApiProxy
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod

/**
 * *　「RMS WEB SERVICE : items.update」の通信処理を行うAPIクラス<br>
 * https://api.rms.rakuten.co.jp/es/1.0/items/update 
 *
 */
class RwsCrawlerItemsUpdateApi<RwsItemApiUpdateItemListForm, RwsItemsUpdateResult> extends AbstractApiProxy {
    /**
     * URLと通信メソッドを初期化して、インスタンスを生成する
     * 
     * @param header 通信ヘッダー情報
     */
    RwsCrawlerItemsUpdateApi(RwsRequestHeaderBean header){
	super('/es/1.0/items/update', header)
	httpMethod = HttpMethod.XML_POST
    }
}
