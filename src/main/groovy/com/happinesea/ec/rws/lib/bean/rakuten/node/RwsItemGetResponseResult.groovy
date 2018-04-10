package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

/**
 * RMS WEB SERVICE : item.get<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/get
 *
 */
class RwsItemGetResponseResult extends RwsResponseXmlResult implements ApiResponseNode {
    RwsItemGetResult itemGetResult
}
