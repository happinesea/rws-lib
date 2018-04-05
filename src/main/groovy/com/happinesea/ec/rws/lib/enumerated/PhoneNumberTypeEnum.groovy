package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum PhoneNumberTypeEnum implements ApiResponseEnum{
    BUYER('1', '注文者'), DELIVERY_ADD('2', '送付先')


    PhoneNumberTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
