package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory

class RwsCsvBuilderTest {

    @Test
    public void testBuilder() {
	List<RwsCategory> categoryList = new ArrayList()
	RwsCategory category1 = new RwsCategory()
	category1.categoryId = 1
	// TODO ci用設定ファイルロード処理が必要
	category1.name = 'fix'
	RwsCategory category2 = new RwsCategory()
	category2.categoryId = 2
	// TODO ci用設定ファイルロード処理が必要
	category2.name = 'fix'
	categoryList.add(category1)
	categoryList.add(category2)


	RwsCsvCategoryHelper helper = new RwsCsvCategoryHelper()
	RwsCsvBuilder builder = new RwsCsvBuilder(null)
	def expectedException = shouldFail(IllegalArgumentException){ builder.builder(categoryList) }

	assertEquals 'csv helper is null.', expectedException.message

	builder = new RwsCsvBuilder(helper)
	assertNull builder.builder(null)

	assertEquals( category1.categoryId
		+ ","+category1.name
		+ ","+builder.csvConfig.lineSeparator
		+ category2.categoryId
		+ ","+category2.name+","+
		builder.csvConfig.lineSeparator, builder.builder(categoryList))
    }
}
