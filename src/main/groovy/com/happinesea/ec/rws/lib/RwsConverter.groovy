package com.happinesea.ec.rws.lib

/**
 * 異なるシステム間のオブジェクトを変換するために用いる変換クラス。
 *
 * @param <S> 変換元のクラス
 * @Deprecated //TODO 未定
 */
abstract class RwsConverter<S> {
    abstract <D> D convert(S source, D dest) ;
}
