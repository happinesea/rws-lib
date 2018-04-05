package com.happinesea.ec.rws.lib.bean.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsResponseXmlResult

/**
 * RMS WEB SERVICE : item.search<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/search
 *
 */
class RwsItemSearchResponseResult extends RwsResponseXmlResult implements ApiResponseNode {
    RwsItemSearchResult itemSearchResult
}
