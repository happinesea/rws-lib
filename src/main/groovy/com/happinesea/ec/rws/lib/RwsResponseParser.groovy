package com.happinesea.ec.rws.lib

/**
 * RWS responseパーサーのマーカーIF
 * 
 * @author loveapple
 *
 */
interface RwsResponseParser {
    public <R> R parse(java.io.InputStream is,Class<R> clz)
}
