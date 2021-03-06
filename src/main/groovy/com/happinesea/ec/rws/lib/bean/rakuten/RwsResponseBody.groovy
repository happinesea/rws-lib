package com.happinesea.ec.rws.lib.bean.rakuten

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * APIのレスポンスBody
 * 
 * 
 *
 */
class RwsResponseBody implements ApiResponseNode {
    /**
     * XML:result
     */
    RwsResponseXmlResult result

    /**
     * 例外発生時のException情報
     */
    Throwable thro
}