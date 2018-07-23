package com.happinesea.ec.rws.lib.util

import static org.junit.Assert.*

import org.junit.Test

class StringUtilsTest {

    @Test
    public void testConvertStringList() {
	assertNull StringUtils.convertStringList(null)
	assertNull StringUtils.convertStringList(new ArrayList())

	List<Long> longList = [1L, 2L, 3L]
	List<String> expected = ['1', '2', '3']
	assertEquals expected, StringUtils.convertStringList(longList)
	longList = [1L, null, 3L]
	expected = ['1', '', '3']
	assertEquals expected, StringUtils.convertStringList(longList)
    }

    @Test
    public void testChangeNull2Empty(){
	assertEquals '', StringUtils.changeNull2Empty(null)
	assertEquals '', StringUtils.changeNull2Empty('')
	assertEquals 'a', StringUtils.changeNull2Empty('a')
    }

    @Test
    public void testChangeFirstCharToLower() {
	assertNull StringUtils.changeFirstCharToLower(null)
	assertEquals '', StringUtils.changeFirstCharToLower('')
	assertEquals 'abc', StringUtils.changeFirstCharToLower('abc')
	assertEquals 'abc', StringUtils.changeFirstCharToLower('Abc')
	assertEquals 'あいう', StringUtils.changeFirstCharToLower('あいう')
	assertEquals '1bc', StringUtils.changeFirstCharToLower('1bc')
    }
}
