package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum RestoreInventoryFlagEnum implements ApiResponseEnum{
    DEFAULT('0', '商品の設定に従う'), RESTORE('1', '強制的に在庫数を変更する'), NO_RESOTRE('2', '在庫連動しない')


    String id
    String description
}
