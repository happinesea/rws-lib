package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseXmlResult

/**
 *RMS WEB SERVICE : shop.categories.get<br>
 *https://api.rms.rakuten.co.jp/es/1.0/categoryapi/shop/categories/get
 *
 */
class RwsCategoriesGetResponseResult extends RwsResponseXmlResult implements ApiResponseNode {
    RwsCategoriesGetResult categoriesGetResult
}
