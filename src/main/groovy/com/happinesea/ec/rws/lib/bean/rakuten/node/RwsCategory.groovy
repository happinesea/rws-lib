package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * カテゴリノード
 */
class RwsCategory implements ApiResponseNode {
    Integer categoryId
    Integer categoryLevel
    String name
    Integer status
    Integer categoryWeight
    List<RwsCategory> childCategories
}
