package com.happinesea.ec.rws.lib

import com.happinesea.HappineseaConfig

/**
 * カテゴリのCSVを出力のヘルパー
 */
class RwsCsvCategoryHelper<RwsCategory> extends RwsCsvHelper {
    RwsCsvCategoryHelper(){
	setCsvKeyList(HappineseaConfig.getInstance().csvLayoutEccubeCategory)
    }
}
