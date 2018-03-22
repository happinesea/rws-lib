package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum SettlementMethodEnum implements ApiResponseEnum{
    CREDIT_CARD('1', 'クレジットカード'), CASH_ON_DELIVERY('2', '代金引換'),
    POSTPAY('3', '後払い'), BANK_TRANSFER('9', '銀行振込'), SE_PREPAYMENT('13', 'セブンイレブン（前払）'),
    OTHER_PREPAYMENT('14', 'ローソン、郵便局ATM等（前払）')


    SettlementMethodEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
