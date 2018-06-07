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
}
