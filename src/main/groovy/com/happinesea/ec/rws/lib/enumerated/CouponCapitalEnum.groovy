package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum CouponCapitalEnum implements ApiResponseEnum{
    SHOP('0', 'ショップ'),  MAKER('1', 'メーカー')
    ,  SERVICE('2', 'サービス')

    CouponCapitalEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
