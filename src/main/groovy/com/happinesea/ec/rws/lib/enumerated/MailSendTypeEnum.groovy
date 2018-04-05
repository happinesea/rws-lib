package com.happinesea.ec.rws.lib.enumerated;

/**
 * 
 *
 */
public enum MailSendTypeEnum implements ApiResponseEnum{
    PC_MB('0', 'PC/モバイル'), PC('1', 'PC'), MB('2', 'モバイル')


    MailSendTypeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
