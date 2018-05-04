package com.happinesea.ec.rws.lib.bean.rakuten.enumerated

/**
 * ステータスの列挙
 *
 */
enum SystemStatusElementEnum implements ApiResponseEnum{
    OK('OK', ''), NG('NG', 'エラーケース')

    SystemStatusElementEnum(String id, String description){
	this.id = id
	this.description = description
    }
    public String id
    public String description
}
