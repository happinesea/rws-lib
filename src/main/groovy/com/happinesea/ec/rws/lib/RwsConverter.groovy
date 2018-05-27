package com.happinesea.ec.rws.lib

/**
 * 異なるシステム間のオブジェクトを変換するために用いる変換クラス。
 *
 * @param <S> 変換元のクラス
 */
abstract class RwsConverter<S> {
    public <D> D convert(S source, D dest) {
    }
}
