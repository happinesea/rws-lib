package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum EmailCarrierCodeEnum implements ApiResponseEnum{
    PC_SB('0', 'PC(@i.softbank.jp"を含む)'),  DOCOMO('1', 'DoCoMo')
    , AU('2', 'au'),  SB('3', 'SoftBank')
    , WILLCOM('5', 'WILLCOM'),  OTHER('99', 'その他')



    EmailCarrierCodeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
