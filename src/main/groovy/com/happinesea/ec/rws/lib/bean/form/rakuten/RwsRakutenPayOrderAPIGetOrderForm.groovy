package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm

/**
 * 「RMS WEB SERVICE : item.search」で利用する通信フォーム<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/search
 *
 */
class RwsRakutenPayOrderAPIGetOrderForm extends RwsBaseForm{

    /**
     * 注文番号リスト
     */
    List<Integer> orderNumberList
}
