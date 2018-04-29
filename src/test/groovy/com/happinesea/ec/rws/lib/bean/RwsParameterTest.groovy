package com.happinesea.ec.rws.lib.bean

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiGetForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiUpdateItemListForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiUpdateItemListForm.ItemsUpdateRequest
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsItem


class RwsParameterTest {

    @Test
    public void testGetXmlString() {
	RwsParameter parameter = new RwsParameter()
	// test null
	assertEquals '', parameter.getXmlString()

	RwsItemApiUpdateItemListForm form = new RwsItemApiUpdateItemListForm()
	form.itemsUpdateRequest = new ItemsUpdateRequest()
	RwsItem item1 = new RwsItem()
	item1.itemUrl = 'test-itemurl'
	List<RwsItem> itemList = new ArrayList()
	itemList.add(item1)
	form.itemsUpdateRequest.items = itemList

	parameter.requestForm = form

	String val ='''
<?xml version="1.0" encoding="UTF-8"?><request><items><item><itemUrl>test-itemurl</itemUrl></item></items></request>
'''
	assertEquals val.trim() ,parameter.getXmlString(true)


    }

    @Test
    public void testGetQueryString() {
	RwsParameter parameter = new RwsParameter()
	// test null
	assertEquals '', parameter.getQueryString()

	RwsItemApiGetForm form = new RwsItemApiGetForm()
	form.itemUrl = 'testUrl(hoge)'

	parameter.requestForm = form

	assertEquals('itemUrl=testUrl%28hoge%29', parameter.getQueryString())


	form.itemUrl = 'testUrl(hoge)change'
	assertEquals('itemUrl=testUrl%28hoge%29', parameter.getQueryString())
    }

    @Test
    public void testGetQueryStringBoolean() {
	RwsItemApiGetForm form = new RwsItemApiGetForm()
	form.itemUrl = 'testUrl(hoge)'

	RwsParameter parameter = new RwsParameter()
	parameter.requestForm = form

	assertEquals('itemUrl=testUrl%28hoge%29', parameter.getQueryString(false))


	form.itemUrl = 'testUrl(hoge)change'
	assertEquals('itemUrl=testUrl%28hoge%29', parameter.getQueryString(false))
	assertNotEquals('itemUrl=testUrl%28hoge%29', parameter.getQueryString(true))
	assertEquals('itemUrl=testUrl%28hoge%29change', parameter.getQueryString(true))
    }
}
