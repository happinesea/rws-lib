package com.happinesea.ec.rws.lib.enumerated

/**
 * @author loveapple
 *
 */
public enum PurchaseSiteTypeEnum implements ApiResponseEnum{
    ALL('0', 'すべて'),  PC('1', 'PCで注文')
    ,  MB('2', 'モバイルで注文'),  SP('3', 'スマートフォンで注文')
    ,  TB('4', 'タブレットで注文')


    PurchaseSiteTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
