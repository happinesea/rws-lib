package com.happinesea.ec.rws.lib.bean.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsResponseResult

/**
 * RMS WEB SERVICE : item.get<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/get
 *
 */
class RwsItemGetResponseResult extends RwsResponseResult implements ApiResponseNode {
    RwsItemGetResult itemGetResult
}
