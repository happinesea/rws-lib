package com.happinesea.ec.rws.lib.bean.rakuten

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.SystemStatusElementEnum

/**
 * RMSのXMLレスポンスの基底クラス
 * 
 *
 */
class RwsResponseXmlResult implements ApiResponseNode {

    /**
     * XMLのレスポンスstatus
     */
    Status status

    /**
     * API Response status
     * 
     * 
     *
     */
    static class Status implements ApiResponseNode{
	String interfaceId
	SystemStatusElementEnum systemStatus
	MessageElementEnum message
	String requestId
	List<String> requests
    }
}
