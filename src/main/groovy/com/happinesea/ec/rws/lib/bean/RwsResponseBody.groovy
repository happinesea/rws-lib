package com.happinesea.ec.rws.lib.bean

/**
 * APIのレスポンスBody
 * 
 * @author loveapple
 *
 */
class RwsResponseBody {
    /**
     * XML:result
     */
    RwsResponseResult result

    /**
     * 例外発生時のException情報
     */
    Throwable thro
}