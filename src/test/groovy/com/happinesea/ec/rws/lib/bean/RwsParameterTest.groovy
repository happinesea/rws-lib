package com.happinesea.ec.rws.lib.bean

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.form.RwsItemApiGetForm


class RwsParameterTest {

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
