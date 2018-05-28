package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.Constant
import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory

class RwsCsvCategoryHelperTest {

    @Test
    public void testConvert() {

	RwsCsvCategoryHelper<RwsCategory> helper = new RwsCsvCategoryHelper()

	assertNull helper.convert(null)

	RwsCategory category = new RwsCategory()
	category.categoryId = 1
	category.name = "テスト用カテゴリ"
	category.status = 1

	List<String> result = helper.convert(category)

	assertEquals String.valueOf(category.categoryId), result[0]
	assertEquals String.valueOf(category.name), result[1]
	assertEquals "", result[2]

	// 存在しないキーのテスト
	List<String> layout = HappineseaConfig.getInstance().csvLayoutEccubeCategory;
	layout[1] = 'hoge'
	helper.csvKeyList = layout
	result = helper.convert(category)

	assertEquals String.valueOf(category.categoryId), result[0]
	assertEquals "", result[1]
	assertEquals "", result[2]

	// レイアウト存在しないテスト
	helper.csvKeyList = null
	assertNull helper.convert(category)

	// 固定値テスト
	layout[1] = "${Constant.CSV_FIX_STR}fix"
	helper.csvKeyList = layout
	result = helper.convert(category)

	assertEquals String.valueOf(category.categoryId), result[0]
	assertEquals "fix", result[1]

    }
}
