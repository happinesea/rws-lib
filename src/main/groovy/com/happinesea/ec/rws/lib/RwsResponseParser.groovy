package com.happinesea.ec.rws.lib

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * RWS responseパーサーのマーカーIF
 * 
 * 
 *
 */
interface RwsResponseParser {
    public <R extends ApiResponseNode> R parse(String is,Class<R> clz)
}
