package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum OrderTypeEnum implements ApiResponseEnum{
    ORDER('1', '注文日'), ORDER_CONFIRM('2', '注文確認日'),
    ORDER_COMPLETE('3', '注文確定日'), DELIVERY('4', '発送日'),
    DELIVERY_COMPLETE('5', '発送完了日'), PAYING('6', '決済確定日')


    String id
    String description
}
