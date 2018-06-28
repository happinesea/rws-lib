package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 商品ノード
 */
class RwsItemRequest implements ApiResponseNode {
    String itemUrl
    String itemNumber
    String itemName
    Integer itemPrice

    static class Image implements ApiResponseNode{
	String imageUrl
	String imageAlt
    }
    static class Option implements ApiResponseNode{
	String optionName
	String optionStyle
	List<OptionValues> optionValues
	static class OptionValues{
	    OptionValue optionValue
	    static class OptionValue{
		String value
	    }
	}
    }
    static class TagId implements ApiResponseNode{
	String tagId
    }
    static class Point implements ApiResponseNode{
	String pointRate
	String pointRateStart
	String pointRateEnd
    }
    static class ItemInventory implements ApiResponseNode{
	String inventoryType
	List<Inventory> inventories
	String verticalName
	String horizontalName
	String inventoryQuantityFlag
	String inventoryDisplayFlag

	static class Inventory implements ApiResponseNode{
	    String inventoryCount
	    String childNoVertical
	    String childNoHorizontal
	    String optionNameVertical
	    String optionNameHorizontal
	    String isBackorderAvailable
	    String normalDeliveryDateId
	    String backorderDeliveryDateId
	    String isBackorder
	    String isRestoreInventoryFlag
	    List<Image> images
	    List<TagId> tagIds
	}
    }
    static class Medicine implements ApiResponseNode{
	String medCaption
	String medAttention
    }
    static class CategoryInfo implements ApiResponseNode{
	String categorySetManageNumber
	Integer categoryId
	String isPluralItemPage
    }
}
