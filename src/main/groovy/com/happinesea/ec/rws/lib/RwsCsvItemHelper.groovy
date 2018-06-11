package com.happinesea.ec.rws.lib

import org.apache.commons.collections.CollectionUtils

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.CategoryInfo
import com.happinesea.ec.rws.lib.util.StringUtils
import com.orangesignal.csv.CsvWriter

/**
 * 商品のCSVを出力のヘルパー
 */
class RwsCsvItemHelper<RwsItem> extends RwsCsvHelper {
    /**
     * カテゴリマスタのリスト
     */
    List<RwsCategory> categoryList
    /**
     * カテゴリマスタのストレージ
     */
    Map<Integer, RwsCategory> categoryStorage

    /**
     * カテゴリのマスタを設定して、ヘルパーのインスタンスを生成する
     * 
     * @param categoryList
     */
    RwsCsvItemHelper(List<RwsCategory> categoryList){
	setCsvKeyList(HappineseaConfig.getInstance().csvLayoutEccubeItem)
	this.categoryList = categoryList
	if(categoryList) {
	    categoryStorage = new HashMap()
	    for(RwsCategory category: categoryList) {
		categoryStorage[category.categoryId] = category
	    }
	}

	for(int i = 0; i < csvKeyList.size(); i++) {
	    if('images.image.imageUrl' == csvKeyList[i]) {
		addNestRowValKeyStorage('images.image.imageUrl', i)
	    }else if('categories.categoryInfo.categoryId' == csvKeyList[i]){
		addNestRowValKeyStorage('categories.categoryInfo.categoryId', i)
	    }else if('categories.categoryInfo.name' == csvKeyList[i]){
		addNestRowValKeyStorage('categories.categoryInfo.name', i)
	    }else if('tagIds.tagId' == csvKeyList[i]){
		addNestRowValKeyStorage('tagIds.tagId', i)
	    }else if('itemInventory.inventories.inventory.inventoryCount' == csvKeyList[i]){
		addNestValKeyStorage('itemInventory.inventories.inventory.inventoryCount', i)
	    }
	}
    }

    @Override
    protected List<String> convertNestRowVal(RwsItem source, List<String> result){
	if(!result) {
	    return result
	}
	//カテゴリ情報を追加
	List<CategoryInfo> categories = source.categories
	List<Integer> categoryIdList = null
	List<String> categoryNameList = null
	if(categories && categoryStorage) {
	    categoryIdList = new ArrayList(categories.size())
	    categoryNameList = new ArrayList(categories.size())
	    for(CategoryInfo category: categories) {
		categoryIdList.add(category.categoryId)
		categoryNameList.add(categoryStorage.get(category.categoryId).name)
	    }

	    // csv形式でカテゴリID、カテゴリ名称を出力
	    StringWriter writer = new StringWriter()
	    CsvWriter cw = new CsvWriter(writer)

	    try {
		cw.writeValues(StringUtils.convertStringList(categoryIdList))
		result.add(nestRowValKeyStorage['categories.categoryInfo.categoryId'], writer.toString().trim())
		writer.flush()
		cw.flush()
		cw.writeValues(categoryNameList)
		result.add(nestRowValKeyStorage['categories.categoryInfo.name'], writer.toString().trim())
	    }finally {
		try {
		    writer.close()
		    cw.close()
		}catch(Exception e) {}
	    }
	}

	return result
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

	return result
    }
}
