package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * お届け時間帯(午前・午後)の列挙
 *
 */
enum ShippingTermEnum implements ApiResponseEnum{
    NONE('0', 'なし'),  AM('1', '午前')
    ,  PM('2', '午後'),  OTHER('3', 'その他')


    ShippingTermEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
