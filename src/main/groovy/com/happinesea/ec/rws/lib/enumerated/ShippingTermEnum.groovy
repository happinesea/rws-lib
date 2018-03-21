package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum ShippingTermEnum implements ApiResponseEnum{
    NONE(0, 'なし'),  AM(1, '午前')
    ,  PM(2, '午後'),  OTHER(3, 'その他')


    Integer id
    String description
}
