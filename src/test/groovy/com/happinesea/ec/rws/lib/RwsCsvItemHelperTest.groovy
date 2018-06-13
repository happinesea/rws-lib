package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.apache.commons.collections.CollectionUtils
import org.junit.Before
import org.junit.Test

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategory
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItem
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemRequest.CategoryInfo

class RwsCsvItemHelperTest {

    List<RwsCategory> categoryList
    List<CategoryInfo> categoryInfoList
    RwsCsvItemHelper helper
    RwsItem item

    @Before
    public void before() {
	categoryList = new ArrayList()
	categoryInfoList = new ArrayList()
	RwsCategory category1 = new RwsCategory()
	CategoryInfo categoryInfo1 = new CategoryInfo()
	category1.categoryId = 1
	category1.name = 'カテゴリ1'
	categoryInfo1.categoryId = 1
	RwsCategory category2 = new RwsCategory()
	CategoryInfo categoryInfo2 = new CategoryInfo()
	category2.categoryId = 2
	category2.name = 'カテ,ゴリ2'
	categoryInfo2.categoryId = 2
	categoryList.add(category1)
	categoryList.add(category2)
	categoryInfoList.add(categoryInfo1)
	categoryInfoList.add(categoryInfo2)
	helper = new RwsCsvItemHelper(categoryList)

	item = new RwsItem()
	item.itemName = '商品名称'
	item.categories = categoryInfoList
    }

    @Test
    public void testRwsCsvItemHelper() {
	assertEquals HappineseaConfig.getInstance().csvLayoutEccubeItem, helper.getCsvKeyList()
    }

    @Test
    public void testAddNestValKeyStorage() {
	def storage = helper.addNestValKeyStorage(null, null)
	assertNotNull storage
	assertEquals 19, storage['itemInventory.inventories.inventory.inventoryCount']

	storage = helper.addNestValKeyStorage('test', 1)
	assertEquals 19, storage['itemInventory.inventories.inventory.inventoryCount']
	assertEquals 1, storage['test']
	assertNull storage['hoge']
    }

    @Test
    public void testAddNestRowValKeyStorage() {
	def storage = helper.addNestRowValKeyStorage(null, null)
	assertNotNull storage
	assertEquals 24, storage['images.image.imageUrl']
	assertEquals 25, storage['categories.categoryInfo.categoryId']
	assertEquals 26, storage['categories.categoryInfo.name']
	assertEquals 27, storage['tagIds.tagId']

	storage = helper.addNestRowValKeyStorage('test', 1)
	assertEquals 24, storage['images.image.imageUrl']
	assertEquals 25, storage['categories.categoryInfo.categoryId']
	assertEquals 26, storage['categories.categoryInfo.name']
	assertEquals 27, storage['tagIds.tagId']
	assertEquals 1, storage['test']
	assertNull storage['itemInventory.inventories.inventory.inventoryCount']
    }

    /*@Test
     public void testConvertRwsItem() {
     RwsItem item = new RwsItem()
     fail("Not yet implemented")
     }*/

    /*
     @Test
     public void testConvertNestVal() {
     RwsItem item = new RwsItem()
     fail("Not yet implemented")
     }*/

    @Test
    public void testConvertNestRowVal() {
	List<String> result = helper.convertNestRowVal(item, [])
	assertTrue CollectionUtils.isEmpty(result)


	List<String> expected = [
	    '',
	    '1',
	    '公開',
	    '商品名称',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '',
	    '0',
	    '0',
	    '',
	    '',
	    '',
	    "1,2",
	    'カテゴリ1,カテ\\,ゴリ2',
	    '',
	    '',
	    '',
	    ''
	]
	assertEquals expected, helper.convert(item)
	result = helper.convert(item)
    }
}
