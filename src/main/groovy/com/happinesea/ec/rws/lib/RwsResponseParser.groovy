package com.happinesea.ec.rws.lib

/**
 * RWS responseパーサーのマーカーIF
 * 
 * @author loveapple
 *
 */
interface RwsResponseParser {
    public <R> R parse(String is,Class<R> clz)
}
