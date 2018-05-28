package com.happinesea.ec.rws.lib

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory

/**
 * 商品のCSVを出力のヘルパー
 */
class RwsCsvItemHelper<RwsItem> extends RwsCsvHelper {
    /**
     * カテゴリマスタのリスト
     */
    List<RwsCategory> categoryList

    Map<String, Integer> overKeyMap = []

    RwsCsvItemHelper(List<RwsCategory> categoryList){
	csvKeyList = HappineseaConfig.getInstance().csvLayoutEccubeItem
	this.categoryList = categoryList
	for(int i = 0; i < csvKeyList.size(); i++) {
	    if('images.image.imageUrl' == csvKeyList[i]) {
		overKeyMap['images.image.imageUrl'] = i
	    }else if('categories.categoryInfo.categoryId' == csvKeyList[i]){
		overKeyMap['categories.categoryInfo.categoryId'] = i
	    }else if('categories.categoryInfo.name' == csvKeyList[i]){
		overKeyMap['categories.categoryInfo.name'] = i
	    }else if('tagIds.tagId' == csvKeyList[i]){
		overKeyMap['tagIds.tagId'] = i
	    }else if('itemInventory.inventories.inventory.inventoryCount' == csvKeyList[i]){
		overKeyMap['itemInventory.inventories.inventory.inventoryCount'] = i
	    }
	}
    }

    /**
     * 商品マスタをCSVに変換するために、独自処理でオーバライド
     * 
     * @param source 
     * @return
     */
    @Override
    List<String> convert(RwsItem source){
	List<String> result = super.convert(source)
	if(CollectionUtils.isEmpty(result)) {
	    return result
	}
    }
}
