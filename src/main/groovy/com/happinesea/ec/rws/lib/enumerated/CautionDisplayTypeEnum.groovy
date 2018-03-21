package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum CautionDisplayTypeEnum implements ApiResponseEnum{
    NONE('0', '表示なし'), WARN('1', '表示あり 注意喚起'), CANCEL('2', '表示あり キャンセル確定')


    String id
    String description
}
