package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
/**
 * 商品検索結果ノード
 */
class RwsItemSearchResult implements ApiResponseNode {
    String code
    Integer numFound
    List<RwsItem> items
}
;