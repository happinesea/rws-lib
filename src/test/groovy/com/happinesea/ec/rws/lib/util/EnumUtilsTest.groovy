package com.happinesea.ec.rws.lib.util

import static groovy.test.GroovyAssert.*
import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsResponseXmlResult.Status
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

class EnumUtilsTest {

    @Test
    public void testGetApiResponseEnum() {
	def expectedException

	expectedException = shouldFail(IllegalArgumentException){
	    EnumUtils.getApiResponseEnum(null, null)
	}
	assertEquals 'Enum class or code is null', expectedException.message

	expectedException = shouldFail(IllegalArgumentException){
	    EnumUtils.getApiResponseEnum(SystemStatusElementEnum, null)
	}
	assertEquals 'Enum class or code is null', expectedException.message

	expectedException = shouldFail(IllegalArgumentException){
	    EnumUtils.getApiResponseEnum(null, 'code')
	}
	assertEquals 'Enum class or code is null', expectedException.message

	expectedException = shouldFail(IllegalArgumentException){
	    EnumUtils.getApiResponseEnum(Status, 'code')
	}
	String clzMsg = Status.getName() + ' is not ApiResponseEnum.'
	assertEquals clzMsg , expectedException.message

	expectedException = shouldFail(IllegalArgumentException){
	    EnumUtils.getApiResponseEnum(SystemStatusElementEnum, 'code')
	}
	clzMsg = 'No enum constant code in ' +  SystemStatusElementEnum.getName()
	assertEquals clzMsg , expectedException.message


	SystemStatusElementEnum e = EnumUtils.getApiResponseEnum(SystemStatusElementEnum, 'OK')
	assertEquals SystemStatusElementEnum.OK, e
    }
}
