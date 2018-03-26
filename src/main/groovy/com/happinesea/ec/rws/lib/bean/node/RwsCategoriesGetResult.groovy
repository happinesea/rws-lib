package com.happinesea.ec.rws.lib.bean.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 */
class RwsCategoriesGetResult implements ApiResponseNode {
    String code
    Integer shopId
    String categorySetManageNumber
    List<Category> categoryList

    static class Category{
	Integer categoryId
	Integer categoryLevel
	String name
	Integer status
	Integer categoryWeight
	List<Category> childCategories
    }
}