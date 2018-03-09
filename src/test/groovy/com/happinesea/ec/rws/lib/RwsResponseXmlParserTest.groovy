package com.happinesea.ec.rws.lib

import static org.junit.Assert.*

import org.junit.Test

class RwsResponseXmlParserTest {
    def packagePath = 'com/happinesea/ec/rws/lib/'

    @Test
    public void testParse() {
	def path = this.getClass().getClassLoader().getResource(packagePath + "item.search.xml").getPath()
	File xml = new File(path)
	assertTrue(xml.exists())
    }
}
