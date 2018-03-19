package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.junit.Test

import com.happinesea.ec.rws.lib.bean.RwsItemResponseResult

import groovy.util.logging.Log4j2

@Log4j2
class RwsResponseXmlParserTest {
    def packagePath = 'com/happinesea/ec/rws/lib/'
    List a
    @Test
    public void testParseItemSearch() {
	def path = this.getClass().getClassLoader().getResource(packagePath + "item.search.xml").getPath()
	File xml = new File(path)
	assertTrue(xml.exists())

	RwsResponseXmlParser<RwsItemResponseResult> parser = new RwsResponseXmlParser<RwsItemResponseResult>();
	RwsItemResponseResult result = parser.parse(new FileInputStream(xml), RwsItemResponseResult.class)

	assertNotNull result
	assertNotNull result.status
	//assertNotNull result.status.interfaceId
	log.debug(result.status.interfaceId)
    }
}
