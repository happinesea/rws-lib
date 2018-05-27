package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 商品カテゴリ一覧ノード
 */
class RwsCategoriesGetResult implements ApiResponseNode {
    String code
    Integer shopId
    String categorySetManageNumber
    List<RwsCategory> categoryList
}