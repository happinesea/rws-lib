package com.happinesea.ec.rws.lib.bean

import com.happinesea.ec.rws.lib.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

/**
 * XML:result
 * 
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
