package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * クーポン発行元の列挙
 *
 */
enum CouponCapitalEnum implements ApiResponseEnum{
    SHOP('0', 'ショップ'),  MAKER('1', 'メーカー')
    ,  SERVICE('2', 'サービス')

    CouponCapitalEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
