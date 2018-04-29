package com.happinesea.ec.rws.lib

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.apache.commons.collections.CollectionUtils
import org.junit.Test

import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.SystemStatusElementEnum
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategoriesGetResponseResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategoriesGetResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategorysetsGetResponseResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategorysetsGetResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItemSearchResponseResult
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategoriesGetResult.Category
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsCategorysetsGetResult.CategorySet

import groovy.util.logging.Log4j2
import groovy.util.slurpersupport.GPathResult

@Log4j2
class RwsResponseXmlParserTest {
    def itemStatus = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>item.search</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f55</requestId>
		<requests>
			<itemUrl>aaa</itemUrl>
		</requests>
    		<none>hoge</none>
	</status>
</result>'''
    @Test
    public void testParse_forError(){
	RwsResponseXmlParser parser = new RwsResponseXmlParser()

	def expectedException = shouldFail(IllegalArgumentException){parser.parse((String)null , String)}
	assertEquals 'Content or clz is null.', expectedException.message
	expectedException = shouldFail(IllegalArgumentException){parser.parse('xml' , null)}
	assertEquals 'Content or clz is null.', expectedException.message

	assertNull parser.parse((GPathResult)null, String)

	RwsItemSearchResponseResult result = parser.parse(itemSearchXmlSuccess, RwsItemSearchResponseResult)
	assertNotNull result
	assertNotNull result.status
	assertEquals 'item.search', result.status.interfaceId
	assertEquals SystemStatusElementEnum.OK, result.status.systemStatus
	assertEquals MessageElementEnum.OK, result.status.message
	assertEquals '714a4983-555f-42d9-aeea-89dae89f2f55', result.status.requestId
    }

    def itemSearchXmlSuccess = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>item.search</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f55</requestId>
		<requests>
			<itemUrl>aaa</itemUrl>
		</requests>
	</status>
	<itemSearchResult>
		<code>200-00</code>
		<numFound>
			10
		</numFound>
		<items>
			<item>
				<itemUrl>a1</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>a2</itemUrl>
				<!-- omission -->
			</item>
			<item>
				<itemUrl>a3</itemUrl>
				<!-- omission -->
			</item>
		</items>
	</itemSearchResult>
</result>'''

    @Test
    public void testParseItemSearch() {

	RwsResponseXmlParser parser = new RwsResponseXmlParser()

	RwsItemSearchResponseResult result = parser.parse(itemSearchXmlSuccess, RwsItemSearchResponseResult)

	assertNotNull result
	assertNotNull result.status
	assertEquals 'item.search', result.status.interfaceId
	assertEquals SystemStatusElementEnum.OK, result.status.systemStatus
	assertEquals MessageElementEnum.OK, result.status.message
	assertEquals '714a4983-555f-42d9-aeea-89dae89f2f55', result.status.requestId

	assertTrue CollectionUtils.isNotEmpty(result.status.requests)

	assertNotNull result.itemSearchResult
	assertEquals '200-00', result.itemSearchResult.code
	assertEquals 10, result.itemSearchResult.numFound

	assertNotNull result.itemSearchResult.items
	assertEquals 3, result.itemSearchResult.items.size()
	assertNotNull result.itemSearchResult.items[0]
	assertNotNull result.itemSearchResult.items[1]
	assertNotNull result.itemSearchResult.items[2]
	assertEquals 'a1', result.itemSearchResult.items[0].itemUrl
	assertEquals 'a2', result.itemSearchResult.items[1].itemUrl
	assertEquals 'a3', result.itemSearchResult.items[2].itemUrl
    }

    def categorysetsGetResult = '''
<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>shop.categorysets.get</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f5x</requestId>
	</status>
	<categorysetsGetResult>
		<code>N000</code>
		<shopId>1</shopId>
		<categorySetList>
			<categorySet>
				<categorySetManageNumber>0</categorySetManageNumber>
				<categorySetName>ブランド品</categorySetName>
				<categorySetStatus>0</categorySetStatus>
				<categorySetOrder>1</categorySetOrder>
			</categorySet>
			<categorySet>
				<categorySetManageNumber>1</categorySetManageNumber>
				<categorySetName>お菓子</categorySetName>
				<categorySetStatus>0</categorySetStatus>
				<categorySetOrder>2</categorySetOrder>
			</categorySet>
		</categorySetList>
	</categorysetsGetResult>
</result>
'''

    @Test
    public void testParseCategorysetsGetResult() {

	RwsResponseXmlParser parser = new RwsResponseXmlParser()

	RwsCategorysetsGetResponseResult result = parser.parse(categorysetsGetResult, RwsCategorysetsGetResponseResult)

	assertNotNull result
	assertNotNull result.status
	assertEquals 'shop.categorysets.get', result.status.interfaceId
	assertEquals SystemStatusElementEnum.OK, result.status.systemStatus
	assertEquals MessageElementEnum.OK, result.status.message
	assertEquals '714a4983-555f-42d9-aeea-89dae89f2f5x', result.status.requestId

	RwsCategorysetsGetResult categorysetsGetResult = result.categorysetsGetResult

	assertNotNull categorysetsGetResult
	assertEquals 'N000', categorysetsGetResult.code
	assertEquals 1, categorysetsGetResult.shopId

	List<CategorySet> categoryList = categorysetsGetResult.categorySetList
	assertTrue CollectionUtils.isNotEmpty(categoryList)
	assertEquals 2, categoryList.size()
	CategorySet category1 = categoryList[0]
	CategorySet category2 = categoryList[1]

	assertEquals '0', category1.categorySetManageNumber
	assertEquals 'ブランド品', category1.categorySetName
	assertEquals 0, category1.categorySetStatus
	assertEquals 1, category1.categorySetOrder
	assertEquals '1', category2.categorySetManageNumber
	assertEquals 'お菓子', category2.categorySetName
	assertEquals 0, category2.categorySetStatus
	assertEquals 2, category2.categorySetOrder
    }


    def categoriesGetResponseResult = '''<?xml version="1.0" encoding="UTF-8"?>
<result>
	<status>
		<interfaceId>shop.categories.get</interfaceId>
		<systemStatus>OK</systemStatus>
		<message>OK</message>
		<requestId>714a4983-555f-42d9-aeea-89dae89f2f55</requestId>
		  
		<!-- To understand the request from client. But, do not record the authentication information.-->
		<requests>
			<categorySetManageNumber>100</categorySetManageNumber>
		</requests>
	</status>
	<categoriesGetResult>
		<code>N000</code>
                <shopId/>
		<categorySetManageNumber>1</categorySetManageNumber>
		<categoryList>
			<category>
				<categoryId>117</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>チョコレート</name>
				<status>0</status>
				<categoryWeight>1</categoryWeight>
				<childCategories/>
			</category>
			<category>
				<categoryId>118</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>ケーキ</name>
				<status>0</status>
				<categoryWeight>2</categoryWeight>
				<childCategories/>
			</category>
			<category>
				<categoryId>119</categoryId>
				<categoryLevel>1</categoryLevel>
				<name>キャンディー</name>
				<status>0</status>
				<categoryWeight>3</categoryWeight>
				<childCategories>
					<category>
                				<categoryId>1191</categoryId>
                				<categoryLevel>2</categoryLevel>
                				<name>キャンディー-1</name>
                				<status>0</status>
                				<categoryWeight>3</categoryWeight>
                				<childCategories>
                					<category>
                                				<categoryId>11911</categoryId>
                                				<categoryLevel>3</categoryLevel>
                                				<name>キャンディー-1-1</name>
                                				<status>0</status>
                                				<categoryWeight>3</categoryWeight>
                                				<childCategories>
                                					<category>
                                                				<categoryId>119111</categoryId>
                                                				<categoryLevel>4</categoryLevel>
                                                				<name>キャンディー-1-1-1</name>
                                                				<status>0</status>
                                                				<categoryWeight>3</categoryWeight>
                                                				<childCategories>
                                                					
                                                				</childCategories>
                                                			</category>
                                					
                                				</childCategories>
                                			</category>
                				</childCategories>
                			</category>
					<category>
                				<categoryId>1192</categoryId>
                				<categoryLevel>2</categoryLevel>
                				<name>キャンディー-2</name>
                				<status>0</status>
                				<categoryWeight>3</categoryWeight>
                				<childCategories>
                					<category>
                                				<categoryId>11954</categoryId>
                                				<categoryLevel>3</categoryLevel>
                                				<name>キャンディー-2-1</name>
                                				<status>0</status>
                                				<categoryWeight>3</categoryWeight>
                                				<childCategories>
                                					
                                				</childCategories>
                                			</category>
                				</childCategories>
                			</category>
				</childCategories>
			</category>
		</categoryList>
	</categoriesGetResult>
</result>'''

    @Test
    public void testParseCategorysGetResult() {

	RwsResponseXmlParser parser = new RwsResponseXmlParser()

	RwsCategoriesGetResponseResult result = parser.parse(categoriesGetResponseResult, RwsCategoriesGetResponseResult)

	assertNotNull result
	assertNotNull result.status
	assertEquals 'shop.categories.get', result.status.interfaceId
	assertEquals SystemStatusElementEnum.OK, result.status.systemStatus
	assertEquals MessageElementEnum.OK, result.status.message
	assertEquals '714a4983-555f-42d9-aeea-89dae89f2f55', result.status.requestId

	assertTrue CollectionUtils.isNotEmpty(result.status.requests)
	//assertEquals 'shop.categories.get', result.status.requests[0]

	RwsCategoriesGetResult categoriesGetResult = result.categoriesGetResult

	assertNotNull categoriesGetResult
	assertEquals 'N000', categoriesGetResult.code
	assertEquals '1', categoriesGetResult.categorySetManageNumber

	List<Category> categoryList = categoriesGetResult.categoryList

	assertTrue CollectionUtils.isNotEmpty(categoryList)
	assertEquals 3, categoryList.size()
	Category category1 = categoryList[0]
	Category category2 = categoryList[1]
	Category category3 = categoryList[2]

	assertEquals 117, category1.categoryId
	assertEquals 1, category1.categoryLevel
	assertEquals 'チョコレート', category1.name
	assertEquals 0, category1.status
	assertEquals 1, category1.categoryWeight
	assertNull category1.childCategories

	assertEquals 118, category2.categoryId
	assertEquals 1, category2.categoryLevel
	assertEquals 'ケーキ', category2.name
	assertEquals 0, category2.status
	assertEquals 2, category2.categoryWeight
	assertNull category2.childCategories

	assertEquals 119, category3.categoryId
	assertEquals 1, category3.categoryLevel
	assertEquals 'キャンディー', category3.name
	assertEquals 0, category3.status
	assertEquals 3, category3.categoryWeight
	assertNotNull category3.childCategories

	assertEquals 2, category3.childCategories.size()
	Category category3_1 = category3.childCategories[0]
	Category category3_2 = category3.childCategories[1]

	// 1つ目再帰の掘り下げ
	assertEquals 1191, category3_1.categoryId
	assertEquals 2, category3_1.categoryLevel
	assertEquals 'キャンディー-1', category3_1.name
	assertEquals 0, category3_1.status
	assertEquals 3, category3_1.categoryWeight
	assertNotNull category3_1.childCategories

	assertEquals 1, category3_1.childCategories.size()
	Category category3_1_1 = category3_1.childCategories[0]

	assertEquals 11911, category3_1_1.categoryId
	assertEquals 3, category3_1_1.categoryLevel
	assertEquals 'キャンディー-1-1', category3_1_1.name
	assertEquals 0, category3_1_1.status
	assertEquals 3, category3_1_1.categoryWeight
	assertNotNull category3_1_1.childCategories

	assertEquals 1, category3_1.childCategories.size()
	Category category3_1_1_1 = category3_1_1.childCategories[0]

	assertEquals 119111, category3_1_1_1.categoryId
	assertEquals 4, category3_1_1_1.categoryLevel
	assertEquals 'キャンディー-1-1-1', category3_1_1_1.name
	assertEquals 0, category3_1_1_1.status
	assertEquals 3, category3_1_1_1.categoryWeight
	assertNull category3_1_1_1.childCategories

	// 2つ目再帰の掘り下げ

	// 1つ目の掘り下げ
	assertEquals 1192, category3_2.categoryId
	assertEquals 2, category3_2.categoryLevel
	assertEquals 'キャンディー-2', category3_2.name
	assertEquals 0, category3_2.status
	assertEquals 3, category3_2.categoryWeight
	assertNotNull category3_2.childCategories

	assertEquals 1, category3_2.childCategories.size()
	Category category3_2_1 = category3_2.childCategories[0]

	assertEquals 11954, category3_2_1.categoryId
	assertEquals 3, category3_2_1.categoryLevel
	assertEquals 'キャンディー-2-1', category3_2_1.name
	assertEquals 0, category3_2_1.status
	assertEquals 3, category3_2_1.categoryWeight
	assertNull category3_2_1.childCategories

    }
}