package com.happinesea.ec.rws.lib.enumerated;

/**
 * @author loveapple
 *
 */
public enum DeliveryCompanyEnum implements ApiResponseEnum{
    OTHER('1000', 'その他')
    ,YAMATO('1001', 'ヤマト運輸')
    ,SAGAWA('1002', '佐川急便')
    ,JPOST('1003', '日本郵便')
    ,SEINO('1004', '西濃運輸')
    ,SEIBU('1005', '西部運輸')
    ,FUKUTSU('1006', '福山通運')
    ,MEITETSU('1007', '名鉄運輸')
    ,TONAMI('1008', 'トナミ運輸')
    ,DAICHI('1009', '第一貨物')
    ,NIGATA('1010', '新潟運輸')
    ,CHUETSU('1011', '中越運送')
    ,OKAYAMA('1012', '岡山県貨物運送')
    ,KURUME('1013', '久留米運送')
    ,SANYO('1014', '山陽自動車運送')
    ,NITORA('1015', '日本トラック')
    ,ECHOHAI('1016', 'エコ配')
    ,EMS('1017', 'EMS')
    ,DHL('1018', 'DHL')
    ,FEDEX('1019', 'FedEx')
    ,UPS('1020', 'UPS')
    ,NITSU('1021', '日本通運')
    ,TNT('1022', 'TNT')
    ,OCS('1023', 'OCS')
    ,USPS('1024', 'USPS')
    ,SF_EXPRESS('1025', 'SFエクスプレス')
    ,ARAMEX('1026', 'Aramex')
    ,SCH('1027', 'SGHグローバル・ジャパン')


    String id
    String description
}
