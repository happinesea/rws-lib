package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * 
 *
 */
class RwsItemUpdateResult implements ApiResponseNode {
    String code
    List<RwsErrorMessage> errorMessages
    RwsItem item
}
