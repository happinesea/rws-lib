package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 端末のキャリアの列挙 
 *
 */
enum CarrierCodeEnum implements ApiResponseEnum {
    FP_DOCOMO('1', 'モバイル(docomo) フィーチャーフォン'),  FP_KDDI('2', 'モバイル(KDDI) フィーチャーフォン')
    ,  FP_SB('3', 'モバイル(Softbank) フィーチャーフォン'),  FP_WILLCOM('5', 'モバイル(WILLCOM) フィーチャーフォン')
    ,  SP_IOS('11', 'スマートフォン（iPhone系）'),  SP_ANDROID('12', 'スマートフォン（Android系）')
    ,  SP_OTHER('19', 'スマートフォン（その他）'),  TB_IOS('21', 'タブレット（iPad系）')
    ,  TB_ANDROID('22', 'タブレット（Android系）'),  TB_OTHER('29', 'タブレット（その他）')
    ,  OTHER('99', 'その他　不明な場合も含む')

    CarrierCodeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
