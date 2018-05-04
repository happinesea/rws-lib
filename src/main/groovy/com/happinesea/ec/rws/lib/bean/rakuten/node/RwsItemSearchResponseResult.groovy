package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

/**
 * 「RMS WEB SERVICE : item.search」のレスポンス結果<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/search
 *
 */
class RwsItemSearchResponseResult extends RwsResponseXmlResult implements ApiResponseNode {
    RwsItemSearchResult itemSearchResult
}
