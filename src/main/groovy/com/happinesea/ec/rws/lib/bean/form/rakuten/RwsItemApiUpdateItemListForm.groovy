package com.happinesea.ec.rws.lib.bean.form.rakuten

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest

/**
 * 
 *
 */
class RwsItemApiUpdateItemListForm extends RwsBaseForm{

    ItemsUpdateRequest itemsUpdateRequest

    /**
     * 商品情報リスト更新リクエスト
     */
    static class ItemsUpdateRequest implements ApiResponseNode{
	List<RwsItemRequest> items
    }
}
