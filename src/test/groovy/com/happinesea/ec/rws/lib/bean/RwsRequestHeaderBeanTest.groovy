package com.happinesea.ec.rws.lib.bean

import static org.junit.Assert.*

import com.happinesea.ec.rws.lib.bean.enumerated.ApiTypeEnum

import spock.lang.Specification

/**
 * 
 *
 */
class RwsRequestHeaderBeanTest extends Specification{

    def testGetAuthorization_secret_null() {
	def target = new RwsRequestHeaderBean()

	expect :
	null == target.getAuthorization()
    }
    def testGetAuthorization_licenseKey_null() {
	def target = new RwsRequestHeaderBean()
	target.serviceSecret = 1

	expect :
	null == target.getAuthorization()
    }

    def testGetAuthorization_success() {

	def target = new RwsRequestHeaderBean()

	target.serviceSecret = 1
	target.licenseKey = 1
	target.apiKey = 2

	assertEquals "ESA "+"1:1".bytes.encodeBase64().toString(), target.getAuthorization()

	target = new RwsRequestHeaderBean(ApiTypeEnum.WOWMA)
	target.serviceSecret = 1
	target.licenseKey = 1
	target.apiKey = 2
	assertEquals "Bearer "+"2", target.getAuthorization()
    }
}