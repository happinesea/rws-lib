package com.happinesea.ec.rws.lib

import com.happinesea.ec.rws.lib.bean.RwsResponseResult

/**
 * RWS responseパーサーのマーカーIF
 * 
 * @author loveapple
 *
 */
interface RwsResponseParser {
    public <R extends RwsResponseResult> R parse(String is,Class<R> clz)
}
