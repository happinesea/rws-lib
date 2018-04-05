package com.happinesea.ec.rws.lib.bean

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