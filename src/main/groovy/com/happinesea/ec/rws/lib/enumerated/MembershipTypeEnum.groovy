package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum MembershipTypeEnum implements ApiResponseEnum{
    OTHER('0', '楽天プレミアムでも楽天学割対象受注でもない'),  PRIM('1', '楽天プレミアム対象受注である')
    ,  STUDENT('2', '楽天学割対象受注である')


    MembershipTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
