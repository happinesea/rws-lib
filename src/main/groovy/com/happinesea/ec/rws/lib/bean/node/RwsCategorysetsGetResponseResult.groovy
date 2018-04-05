package com.happinesea.ec.rws.lib.bean.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.RwsResponseXmlResult

/**
 *RMS WEB SERVICE : shop.categorysets.get<br>
 *https://api.rms.rakuten.co.jp/es/1.0/categoryapi/shop/categorysets/get
 *
 */
class RwsCategorysetsGetResponseResult extends RwsResponseXmlResult implements ApiResponseNode {
    RwsCategorysetsGetResult categorysetsGetResult
}
