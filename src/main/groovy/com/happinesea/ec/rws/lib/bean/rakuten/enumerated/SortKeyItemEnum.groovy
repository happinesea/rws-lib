package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * ソートキーの列挙
 *
 */
enum SortKeyItemEnum implements ApiResponseEnum{
    ITEM_REG_TIME('itemRegTime', '商品登録日'),  ITEM_PRICE('itemPrice', '販売価格')


    SortKeyItemEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
