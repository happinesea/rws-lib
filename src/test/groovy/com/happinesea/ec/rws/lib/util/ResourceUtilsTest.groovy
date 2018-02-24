package com.happinesea.ec.rws.lib.util

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.HappineseaConfig

class ResourceUtilsTest {

    @Test
    public void testGetResourceByClassPath() {
	HappineseaConfig config = HappineseaConfig.getInstance()
	println config.version
	println config.httpClientAgent
	//println ResourceUtils.getResourceByClassPath('com/happinesea/happinesea_config.groovy')
    }
}
