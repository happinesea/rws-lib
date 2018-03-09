package com.happinesea.ec.rws.lib

import com.happinesea.ec.rws.lib.bean.RwsResponseBody

/**
 * RWS responseパーサーのマーカーIF
 * 
 * @author loveapple
 *
 */
interface RwsResponseParser<R extends RwsResponseBody> {
    R parse(java.io.InputStream is)
}
