package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm

/**
 * 「RMS WEB SERVICE : item.get」で利用する通信フォーム<br>
 * https://api.rms.rakuten.co.jp/es/1.0/item/get
 * 
 */
class RwsItemApiGetForm extends RwsBaseForm{
    /**
     * 商品管理番号
     */
    String itemUrl
}
