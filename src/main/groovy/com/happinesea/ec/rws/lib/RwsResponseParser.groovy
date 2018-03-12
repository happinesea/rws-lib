package com.happinesea.ec.rws.lib

import com.happinesea.ec.rws.lib.bean.RwsResponseResult

/**
 * RWS responseパーサーのマーカーIF
 * 
 * @author loveapple
 *
 */
interface RwsResponseParser<R extends RwsResponseResult> {
    R parse(java.io.InputStream is)
}
