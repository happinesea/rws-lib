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

    static class Image{
	String imageUrl
	String imageAlt
    }
    static class Option{
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
    static class TagId{
	String tagId
    }
    static class Point{
	String pointRate
	String pointRateStart
	String pointRateEnd
    }
    static class ItemInventory{
	String inventoryType
	String inventories
	Inventory inventory
	String verticalName
	String horizontalName
	String inventoryQuantityFlag
	String inventoryDisplayFlag

	static class Inventory{
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
    static class Medicine{
	String medCaption
	String medAttention
    }
    static class CategoryInfo{
	String categorySetManageNumber
	String categoryId
	String isPluralItemPage
    }
}
