package com.happinesea

import static org.junit.Assert.*

import org.junit.Test

class HappineseaConfigTest {

    @Test
    public void testGetInstance() {
	HappineseaConfig config = HappineseaConfig.getInstance();

	assertNotNull(config);

	HappineseaConfig config2 = HappineseaConfig.getInstance();

	assertTrue(config == config2)
    }
}
