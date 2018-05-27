package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory

class RwsCsvBuilderTest {

    @Test
    public void testBuilder() {
	RwsCsvCategoryHelper helper = new RwsCsvCategoryHelper()
	RwsCsvBuilder builder = new RwsCsvBuilder(helper)

	List<RwsCategory> categoryList = new ArrayList()
	RwsCategory category1 = new RwsCategory()
	category1.categoryId = 1
	category1.name = 'カテゴリ1'
	RwsCategory category2 = new RwsCategory()
	category2.categoryId = 1
	category2.name = 'カテゴリ2'
	categoryList.add(category1)
	categoryList.add(category2)

	assert "${category1.categoryId}, ${category1.name}, \r\n${category1.categoryId}, ${category1.name}, ", builder.builder(categoryList)
    }
}
