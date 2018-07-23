package com.happinesea.ec.rws.lib.util

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.rakuten.RwsResponseJsonResult.MessageModel
import com.happinesea.ec.rws.lib.bean.rakuten.node.RwsSearchOrderResult

class BeanUtilsTest {

    @Test
    public void testSetProperty() {
	RwsSearchOrderResult source = new RwsSearchOrderResult()

	source.orderNumberList

	assertNull source.MessageModelList

	List<MessageModel> MessageModelList = new ArrayList()
	MessageModel mm = new MessageModel()
	MessageModelList.add(mm)
	BeanUtils.setProperty(source, 'MessageModelList', MessageModelList)

	assertNull source.orderNumberList

	List<String> orderNumberList = new ArrayList()
	orderNumberList.add("1")
	orderNumberList.add("2")
	BeanUtils.setProperty(source, 'orderNumberList', orderNumberList)

	assertNotNull source.MessageModelList
	assertEquals 1, source.MessageModelList.size()
	assertEquals MessageModelList, source.MessageModelList

	assertNotNull source.orderNumberList
	assertEquals 2, source.orderNumberList.size()
	assertEquals orderNumberList, source.orderNumberList
    }
}
