package com.happinesea.ec.rws.lib.bean.rakuten.enumerated

/**
 * メッセージの列挙
 *
 */
enum MessageElementEnum implements ApiResponseEnum {
    OK('OK', '')
    , ParameterError('ParameterError', 'パラメータエラー	')
    ,Request_data_is_wrong_format('Request data is wrong format', 'リクエストのフォーマットエラー	')
    , AuthError('AuthError', '認証エラー	')
    , AccessLimit('AccessLimit', 'リクエスト閾値エラー')
    , SystemError('SystemError()', '予期せぬエラー')
    , Method_Not_Allowed('Method Not Allowed', '許可されていないHTTPメソッド')

    MessageElementEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
