package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 商品情報取得ノード
 */
class RwsItemGetResult implements ApiResponseNode {
    def code
    RwsItem rwsItem
}
;