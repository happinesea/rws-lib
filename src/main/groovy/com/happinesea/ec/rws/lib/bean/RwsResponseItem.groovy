package com.happinesea.ec.rws.lib.bean

/**
 * @author loveapple
 *
 */
class RwsResponseItem implements ApiResponseNode {
    String itemUrl
    String itemNumber
    String itemName
    String itemPrice
    String genreId
    String catalogId
    String catalogIdExemptionReason
    String whiteBgImageUrl
    List<Image> images
    String descriptionForPC
    String descriptionForMobile
    String descriptionForSmartPhone
    String movieUrl
    List<Option> options
    List<TagId> tagIds
    String catchCopyForPC
    String catchCopyForMobile
    String descriptionBySalesMethod
    String isSaleButton
    String isDocumentButton
    String isInquiryButton
    String isStockNoticeButton
    String displayMakerContents
    String itemLayout
    String isIncludedTax
    String isIncludedPostage
    String isIncludedCashOnDeliveryPostage
    String displayPrice
    String orderLimit
    String postage
    String postageSegment1
    String postageSegment2
    String isNoshiEnable
    String isTimeSale
    String timeSaleStartDateTime
    String timeSaleEndDateTime
    String isUnavailableForSearch
    String limitedPasswd
    String isAvailableForMobile
    String isDepot
    String detailSellType
    String releaseDate
    Point point
    ItemInventory itemInventory
    String asurakuDeliveryId
    String deliverySetId
    String sizeChartLinkCode
    String reviewDisp
    Medicine medicine
    String displayPriceId
    List<CategoryInfo> categoryInfo
    String itemWeight
    String layoutCommonId
    String layoutMapId
    String textSmallId
    String lossLeaderId
    String textLargeId
    String registDate

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
