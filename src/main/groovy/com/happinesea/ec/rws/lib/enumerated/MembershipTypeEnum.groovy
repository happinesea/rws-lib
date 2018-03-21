package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum MembershipTypeEnum implements ApiResponseEnum{
    OTHER('0', '楽天プレミアムでも楽天学割対象受注でもない'),  PRIM('1', '楽天プレミアム対象受注である')
    ,  STUDENT('2', '楽天学割対象受注である')


    String id
    String description
}
