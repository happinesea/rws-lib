package com.happinesea.ec.rws.lib.bean

/**
 * APIのレスポンスBody
 * 
 * @author loveapple
 *
 */
class RwsResponseBody implements ApiResponseNode {
    /**
     * XML:result
     */
    RwsResponseResult result

    /**
     * 例外発生時のException情報
     */
    Throwable thro
}