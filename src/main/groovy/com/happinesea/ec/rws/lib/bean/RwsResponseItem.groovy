package com.happinesea.ec.rws.lib.bean

/**
 * @author loveapple
 *
 */
class RwsResponseItem {
    def itemUrl
    def itemNumber
    def itemName
    def itemPrice
    def genreId
    def catalogId
    def catalogIdExemptionReason
    def whiteBgImageUrl
    List<Image> images
    def descriptionForPC
    def descriptionForMobile
    def descriptionForSmartPhone
    def movieUrl
    List<Option> options
    List<TagId> tagIds
    def catchCopyForPC
    def catchCopyForMobile
    def descriptionBySalesMethod
    def isSaleButton
    def isDocumentButton
    def isInquiryButton
    def isStockNoticeButton
    def displayMakerContents
    def itemLayout
    def isIncludedTax
    def isIncludedPostage
    def isIncludedCashOnDeliveryPostage
    def displayPrice
    def orderLimit
    def postage
    def postageSegment1
    def postageSegment2
    def isNoshiEnable
    def isTimeSale
    def timeSaleStartDateTime
    def timeSaleEndDateTime
    def isUnavailableForSearch
    def limitedPasswd
    def isAvailableForMobile
    def isDepot
    def detailSellType
    def releaseDate
    Point point
    ItemInventory itemInventory
    def asurakuDeliveryId
    def deliverySetId
    def sizeChartLinkCode
    def reviewDisp
    Medicine medicine
    def displayPriceId
    List<CategoryInfo> categoryInfo
    def itemWeight
    def layoutCommonId
    def layoutMapId
    def textSmallId
    def lossLeaderId
    def textLargeId
    def registDate

    class Image{
	def imageUrl
	def imageAlt
    }
    class Option{
	def optionName
	def optionStyle
	List<OptionValues> optionValues
	class OptionValues{
	    OptionValue optionValue
	    class OptionValue{
		def value
	    }
	}
    }
    class TagId{
	def tagId
    }
    class Point{
	def pointRate
	def pointRateStart
	def pointRateEnd
    }
    class ItemInventory{
	def inventoryType
	def inventories
	Inventory inventory
	def verticalName
	def horizontalName
	def inventoryQuantityFlag
	def inventoryDisplayFlag

	class Inventory{
	    def inventoryCount
	    def childNoVertical
	    def childNoHorizontal
	    def optionNameVertical
	    def optionNameHorizontal
	    def isBackorderAvailable
	    def normalDeliveryDateId
	    def backorderDeliveryDateId
	    def isBackorder
	    def isRestoreInventoryFlag
	    List<Image> images
	    List<TagId> tagIds
	}
    }
    class Medicine{
	def medCaption
	def medAttention
    }
    class CategoryInfo{
	def categorySetManageNumber
	def categoryId
	def isPluralItemPage
    }
}
