package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 商品カテゴリ一覧ノード
 */
class RwsCategoriesGetResult implements ApiResponseNode {
    String code
    Integer shopId
    String categorySetManageNumber
    List<Category> categoryList

    static class Category implements ApiResponseNode{
	Integer categoryId
	Integer categoryLevel
	String name
	Integer status
	Integer categoryWeight
	List<Category> childCategories
    }
}