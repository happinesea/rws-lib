package com.happinesea.ec.rws.lib.bean

import com.happinesea.ec.rws.lib.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

/**
 * XML:result
 * 
 * @author loveapple
 *
 */
class RwsResponseResult implements ApiResponseNode {

    /**
     * XMLのレスポンスstatus
     */
    Status status

    /**
     * API Response status
     * 
     * @author loveapple
     *
     */
    static class Status implements ApiResponseNode{
	def interfaceId
	SystemStatusElementEnum systemStatus
	MessageElementEnum message
	def requestId
	def requests
    }
}
