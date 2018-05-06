package com.happinesea.ec.rws.lib.bean.rakuten.enumerated

/**
 * 商品更新APIの列挙
 *
 */
enum ItemApiResponseCodeEnum implements ApiResponseEnum {
    N000('N000','正常終了(検索結果あり)'),
    S001('S001','全サービス停止(メンテナンス中)'),
    S002('S002','該当店舗サービス停止'),
    S011('S011','楽天側エラー'),
    S021('S021','楽天側エラー'),
    S031('S031','楽天側エラー'),
    S999('S999','楽天側エラー'),
    C001('C001','該当する商品IDが存在しない場合 (検索結果が存在しない)'),
    C011('C011','指定された店舗IDが存在しない場合 (店舗が存在しない)'),
    C113('C113','パラメータのフォーマットエラー'),
    C114('C114','商品情報更新ロック'),
    C201('C201','パラメータのイベントIDが存在しない'),
    C204('C204','商品IDのフォーマットが正しくない'),
    C219('C219','不要なデータが入っている'),
    C301('C301','処理済み')

    ItemApiResponseCodeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
