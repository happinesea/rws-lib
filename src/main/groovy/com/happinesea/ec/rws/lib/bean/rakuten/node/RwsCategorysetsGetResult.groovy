package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 */
class RwsCategorysetsGetResult implements ApiResponseNode {
    String code
    Integer shopId
    List<CategorySet> categorySetList

    static class CategorySet{
	String categorySetManageNumber
	Integer categorySetId
	String categorySetName
	Integer categorySetStatus
	Integer categorySetOrder
    }
}