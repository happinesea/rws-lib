package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItem

/**
 * 
 *
 */
class RwsItemApiUpdateForm extends RwsBaseForm{

    ItemUpdateRequest itemUpdateRequest
    /**
     * 商品情報リスト更新要求
     */
    static class ItemUpdateRequest{
	RwsItem item
    }
}
