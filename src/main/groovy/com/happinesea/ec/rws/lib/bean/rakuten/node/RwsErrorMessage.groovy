package com.happinesea.ec.rws.lib.bean.rakuten.node

import com.happinesea.ec.rws.lib.bean.ApiResponseNode

/**
 * エラーメッセージのノード
 *
 */
class RwsErrorMessage implements ApiResponseNode {
    String errorId
    String fieldId
    String msg
    Integer listIndex
    Integer listIndex2
}
