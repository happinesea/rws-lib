package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 電話番号検索種別の列挙
 *
 */
enum PhoneNumberTypeEnum implements ApiResponseEnum{
    BUYER('1', '注文者'), DELIVERY_ADD('2', '送付先')


    PhoneNumberTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
