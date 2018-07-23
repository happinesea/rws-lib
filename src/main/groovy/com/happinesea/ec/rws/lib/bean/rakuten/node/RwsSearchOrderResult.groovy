package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseJsonResult

/**
 *　「RMS WEB SERVICE : searchOrder」のレスポンス結果<br>
 *https://api.rms.rakuten.co.jp/es/2.0/order/searchOrder/
 *
 */
public class RwsSearchOrderResult extends RwsResponseJsonResult {
    /**
     * 注文番号リスト
     */
    List<String> orderNumberList
    /**
     * ページングレスポンスモデル
     */
    PaginationResponseModel PaginationResponseModel

    /**
     * ページングレスポンスモデル
     */
    static class PaginationResponseModel implements ApiResponseNode{
	/**
	 * 総結果数
	 */
	Integer totalRecordsAmount
	/**
	 * 総ページ数
	 */
	Integer totalPages
	/**
	 * リクエストページ番号
	 */
	Integer requestPage
    }
}
