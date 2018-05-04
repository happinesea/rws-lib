package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * CVS(コンビニ)の列挙
 *
 */
enum CvsCodeEnum implements ApiResponseEnum{
    /** ファミリーマート */
    FM('1', 'ファミリーマート')
    /** ミニストップ */
    ,MS('20', 'ミニストップ')
    /** サークルK */
    ,SK('40', 'サークルK')
    /** サンクス */
    ,S('41', 'サンクス')
    /** ローソン */
    ,LS('50', 'ローソン')
    /** 郵便局 */
    ,POST('60', '郵便局')
    /** スリーエフ */
    ,SF('70', 'スリーエフ')
    /** エブリワン */
    ,EO('71', 'エブリワン')
    /** ココストア */
    ,CS('72', 'ココストア')
    /** セーブオン */
    ,SO('74', 'セーブオン')
    /** デイリーヤマザキ */
    ,DY('80', 'デイリーヤマザキ')
    /** ヤマザキデイリーストア */
    ,YD('81', 'ヤマザキデイリーストア')
    /** ニューヤマザキデイリーストア */
    ,NY('82', 'ニューヤマザキデイリーストア')
    /** ニューデイズ */
    ,ND('85', 'ニューデイズ')
    /** ポプラ */
    ,PL('90', 'ポプラ')
    /** くらしハウス */
    ,KH('91', 'くらしハウス')
    /** スリーエイト */
    ,T8('92', 'スリーエイト')
    /** 生活彩家 */
    ,SS('93', '生活彩家')


    CvsCodeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
