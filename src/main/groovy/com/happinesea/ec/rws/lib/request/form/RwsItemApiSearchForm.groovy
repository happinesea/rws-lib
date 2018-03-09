package com.happinesea.ec.rws.lib.request.form

import com.happinesea.ec.rws.lib.enumerated.SortKeyItemEnum
import com.happinesea.ec.rws.lib.enumerated.SortOrderItemEnum

/**
 * @author loveapple
 *
 */
class RwsItemApiSearchForm extends RwsItemApiGetForm{

    /**
     * 商品名
     */
    String itemName
    /**
     * PC用キャッチコピー
     */
    String catchcopy
    /**
     *カタログID
     */
    String catalogId
    /**
     *全商品ディレクトリID
     */
    int genreId
    /**
     *価格下限
     */
    int itemPriceFrom
    /**
     *価格上限
     */
    int itemPriceTo
    /**
     *倉庫フラグ
     */
    int depotFlg
    /**
     *商品モバイルフラグ
     */
    int itemMobileFlg
    /**
     *闇市フラグ
     */
    int limitedFlg
    /**
     *送料別フラグ
     */
    int postageFlg
    /**
     *検索結果取得開始位置
     */
    int offset
    /**
     *検索結果取得上限数
     */
    int limit
    /**
     *ソートキー
     */
    SortKeyItemEnum sortKey
    /**
     *ソート
     */
    SortOrderItemEnum sortOrder
}
