package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum SearchKeywordTypeEnum implements ApiResponseEnum{
    NONE(0, 'なし'), ITEM_NAME(1, '商品名'), ITEM_NUMBER(2, '商品番号'),
    MEMO(3, 'ひとことメモ'), BUYER_NAME(4, '注文者お名前'), BUYER_KANA(5, '注文者お名前フリガナ'),
    DELIVERY_NAME(6, '送付先お名前')


    Integer id
    String description
}
