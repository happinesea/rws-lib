package com.happinesea.ec.rws.lib.bean.enumerated;

/**
 * API種別の列挙
 *
 */
enum ApiTypeEnum {
    /**
     * API種別：楽天
     */
    RAKUTEN('rakuten', '注文日')
    /**
     * API種別：WOWMA
     */
    , WOWMA('wowma', '注文確認日')

    ApiTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
