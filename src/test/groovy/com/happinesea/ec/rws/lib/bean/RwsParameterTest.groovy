package com.happinesea.ec.rws.lib.bean

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiGetForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiUpdateItemListForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsRakutenPayOrderAPIGetOrderForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsRakutenPayOrderAPISearchOrderForm
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiUpdateItemListForm.ItemsUpdateRequest
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsRakutenPayOrderAPISearchOrderForm.PaginationRequestModel
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
<?xml version="1.0" encoding="UTF-8"?><request><itemsUpdateRequest><items><item><itemUrl>test-itemurl</itemUrl></item></items></itemsUpdateRequest></request>
'''
	assertEquals val.trim() ,parameter.getXmlString(true)

	assertEquals parameter.getXmlString(), parameter.getXmlString(true)
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

    @Test
    public void testGetJsonString() {
	RwsParameter parameter = new RwsParameter()
	// test null
	assertEquals '', parameter.getJsonString()
	assertEquals '', parameter.getJsonString(false)
	assertEquals '', parameter.getJsonString(true)

	RwsRakutenPayOrderAPIGetOrderForm form = new RwsRakutenPayOrderAPIGetOrderForm()
	form.orderNumberList = new ArrayList()
	form.orderNumberList.add(1)
	form.orderNumberList.add(2)
	parameter.requestForm = form

	assertEquals '{"orderNumberList":[1,2]}', parameter.getJsonString()
	assertEquals '{"orderNumberList":[1,2]}', parameter.getJsonString()

	RwsRakutenPayOrderAPISearchOrderForm searchOrderForm = new RwsRakutenPayOrderAPISearchOrderForm()
	searchOrderForm.orderProgressList = [1, 2]
	searchOrderForm.subStatusIdList = [3, 4]
	searchOrderForm.dateType = 0
	searchOrderForm.paginationRequestModel = new PaginationRequestModel()
	searchOrderForm.paginationRequestModel.requestPage = 1
	searchOrderForm.paginationRequestModel.requestRecordsAmount = 100
	parameter.requestForm = searchOrderForm

	// キャッシュを更新しないと、結果が変わらない
	assertEquals '{"orderNumberList":[1,2]}', parameter.getJsonString()
	assertEquals '{"searchKeywordType":null,"purchaseSiteType":null,"couponUseFlag":null,"overseasFlag":null,"startDatetime":null,"dateType":0,"paginationRequestModel":{"requestRecordsAmount":100,"requestPage":1,"sortModelList":null},"asurakuFlag":null,"deliveryName":null,"orderProgressList":[1,2],"mailSendType":null,"ordererMailAddress":null,"orderTypeList":null,"reserveNumber":null,"settlementMethod":null,"subStatusIdList":[3,4],"shippingNumberBlankFlag":null,"drugFlag":null,"searchKeyword":null,"phoneNumberType":null,"shippingDateBlankFlag":null,"endDatetime":null,"phoneNumber":null}', parameter.getJsonString(true)

    }
}
