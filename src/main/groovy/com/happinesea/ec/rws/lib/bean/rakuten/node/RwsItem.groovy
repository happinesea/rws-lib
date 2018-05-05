package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.CategoryInfo
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.Image
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.ItemInventory
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.Medicine
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.Option
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.Point
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.TagId

/**
 * 商品ノード
 */
class RwsItem extends RwsItemRequest implements ApiResponseNode {
    // TODO move to super class

    Integer genreId
    String catalogId
    Integer catalogIdExemptionReason
    String whiteBgImageUrl
    String catchCopyForPC
    String catchCopyForMobile
    Boolean displayMakerContents
    Boolean isIncludedTax
    Boolean isIncludedPostage
    Integer displayPrice
    Boolean isUnavailableForSearch
    Boolean isAvailableForMobile
    Boolean isDepot
    Integer deliverySetId
    List<CategoryInfo> categories
    Integer itemWeight
    Integer layoutCommonId
    Integer layoutMapId
    Integer textSmallId
    Integer lossLeaderId
    Integer textLargeId

    // TODO
    List<Image> images
    String descriptionForPC
    String descriptionForMobile
    String descriptionForSmartPhone
    String movieUrl
    List<Option> options
    List<TagId> tagIds
    String descriptionBySalesMethod
    String isSaleButton
    String isDocumentButton
    String isInquiryButton
    String isStockNoticeButton
    String itemLayout
    String isIncludedCashOnDeliveryPostage
    String orderLimit
    String postage
    String postageSegment1
    String postageSegment2
    String isNoshiEnable
    String isTimeSale
    String timeSaleStartDateTime
    String timeSaleEndDateTime
    String limitedPasswd
    String detailSellType
    String releaseDate
    Point point
    ItemInventory itemInventory
    String asurakuDeliveryId
    String sizeChartLinkCode
    String reviewDisp
    Medicine medicine
    String displayPriceId
    String registDate
}
