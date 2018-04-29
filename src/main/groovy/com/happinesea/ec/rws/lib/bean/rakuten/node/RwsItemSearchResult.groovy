package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
class RwsItemSearchResult implements ApiResponseNode {
    String code
    Integer numFound
    List<RwsItem> rwsItems
}
;