package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 受注経路の列挙
 *
 */
enum DateTypeEnum implements ApiResponseEnum{
    NORMAL('1', '通常購入'), REGULARY('4', '定期購入'),
    DISTRIBUTION('5', '頒布会'), RESERVED('6', '予約商品')


    DateTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
