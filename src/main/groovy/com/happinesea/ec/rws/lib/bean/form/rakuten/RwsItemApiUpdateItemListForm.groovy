package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItem

/**
 * 
 *
 */
class RwsItemApiUpdateItemListForm extends RwsBaseForm{

    ItemsUpdateRequest itemsUpdateRequest

    /**
     * 商品情報リスト更新要求
     */

    static class ItemsUpdateRequest implements ApiResponseNode{
	List<RwsItem> items
    }
}
