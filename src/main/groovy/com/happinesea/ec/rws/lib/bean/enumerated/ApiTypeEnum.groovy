package com.happinesea.ec.rws.lib.bean.enumerated;

/**
 * API種別の列挙
 *
 */
enum ApiTypeEnum {
    /**
     * API種別：楽天
     */
    RAKUTEN('rakuten', '')
    /**
     * API種別：WOWMA
     */
    , WOWMA('wowma', '')
    , BASIC('Basic', '')

    ApiTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
