package com.happinesea.ec.rws.lib.bean

class RwsItemSearchResult implements ApiResponseNode {
    String code
    Integer numFound
    List<RwsResponseItem> items
}
;