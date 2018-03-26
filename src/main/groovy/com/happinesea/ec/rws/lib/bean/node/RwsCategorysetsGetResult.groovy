package com.happinesea.ec.rws.lib.bean.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 */
class RwsCategorysetsGetResult implements ApiResponseNode {
    String code
    String shopId
    List<CategorySet> categorySetList

    static class CategorySet{
	String categorySetManageNumber
	String categorySetId
	String categorySetName
	String categorySetStatus
	String categorySetOrder
    }
}