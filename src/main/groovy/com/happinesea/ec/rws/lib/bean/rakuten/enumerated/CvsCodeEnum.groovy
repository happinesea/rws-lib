package com.happinesea.ec.rws.lib.bean.rakuten.enumerated;

/**
 * 
 *
 */
enum CvsCodeEnum implements ApiResponseEnum{
    FM('1', 'ファミリーマート')
    ,MS('20', 'ミニストップ')
    ,SK('40', 'サークルK')
    ,S('41', 'サンクス')
    ,LS('50', 'ローソン')
    ,POST('60', '郵便局')
    ,SF('70', 'スリーエフ')
    ,EO('71', 'エブリワン')
    ,CS('72', 'ココストア')
    ,SO('74', 'セーブオン')
    ,DY('80', 'デイリーヤマザキ')
    ,YD('81', 'ヤマザキデイリーストア')
    ,NY('82', 'ニューヤマザキデイリーストア')
    ,ND('85', 'ニューデイズ')
    ,PL('90', 'ポプラ')
    ,KH('91', 'くらしハウス')
    ,T8('92', 'スリーエイト')
    ,SS('93', '生活彩家')


    CvsCodeEnum(String id, String description){
	this.id = id
	this.description = description
    }
    String id
    String description
}
