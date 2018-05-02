package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 受注検索種別の列挙
 *
 */
enum OrderTypeEnum implements ApiResponseEnum{
    ORDER('1', '注文日'), ORDER_CONFIRM('2', '注文確認日'),
    ORDER_COMPLETE('3', '注文確定日'), DELIVERY('4', '発送日'),
    DELIVERY_COMPLETE('5', '発送完了日'), PAYING('6', '決済確定日')


    OrderTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
