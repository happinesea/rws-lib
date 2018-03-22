package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum CautionDisplayTypeEnum implements ApiResponseEnum{
    NONE('0', '表示なし'), WARN('1', '表示あり 注意喚起'), CANCEL('2', '表示あり キャンセル確定')


    CautionDisplayTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
