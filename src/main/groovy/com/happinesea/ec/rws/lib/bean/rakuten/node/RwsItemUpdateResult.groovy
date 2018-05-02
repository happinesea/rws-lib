package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 商品更新結果ノード
 *
 */
class RwsItemUpdateResult implements ApiResponseNode {
    String code
    List<RwsErrorMessage> errorMessages
    RwsItem item
}
