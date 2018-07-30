package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm

/**
 * 「RMS WEB SERVICE : item.search」で利用する通信フォーム<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/search
 *
 */
class RwsRakutenPayOrderAPISearchOrderForm extends RwsBaseForm{

    /** ステータスリスト*/
    List <Integer> orderProgressList
    /** サブステータスIDリスト　*/
    List <Integer> subStatusIdList
    /** 期間検索種別　*/
    Integer dateType
    /** 期間検索開始日時 */
    Date startDatetime
    /** 期間検索終了日時　*/
    Date endDatetime
    /** 販売種別リスト　*/
    List <Integer> orderTypeList
    /** 支払方法名　*/
    Integer settlementMethod
    /** 配送方法　*/
    String deliveryName
    /** 発送日未指定有無フラグ　*/
    Integer shippingDateBlankFlag
    /** お荷物伝票番号未指定有無フラグ　*/
    Integer shippingNumberBlankFlag
    /** 検索キーワード種別　*/
    Integer searchKeywordType
    /** 検索キーワード　*/
    String searchKeyword
    /** 注文メールアドレス種別　*/
    Integer mailSendType
    /** 注文者メールアドレス　*/
    String ordererMailAddress
    /** 電話番号種別　*/
    Integer phoneNumberType
    /** 電話番号　*/
    String phoneNumber
    /** 申込番号　*/
    String reserveNumber
    /** 購入サイトリスト　*/
    Integer purchaseSiteType
    /** あす楽希望フラグ　*/
    Integer asurakuFlag
    /** クーポン利用有無フラグ　*/
    Integer couponUseFlag
    /** 医薬品受注フラグ　*/
    Integer drugFlag
    /** 海外かご注文フラグ　*/
    Integer overseasFlag
    /** ページングリクエストモデル　*/
    PaginationRequestModel paginationRequestModel

    static class PaginationRequestModel{
	Integer requestRecordsAmount
	Integer requestPage
	List<SortModel> SortModelList
    }

    static class SortModel{
	Integer sortColumn
	Integer sortDirection
    }
}

