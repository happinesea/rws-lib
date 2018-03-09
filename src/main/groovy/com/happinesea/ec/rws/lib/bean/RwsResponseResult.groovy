package com.happinesea.ec.rws.lib.bean

import com.happinesea.ec.rws.lib.enumerated.MessageElementEnum
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

/**
 * XML:result
 * 
 * @author loveapple
 *
 */
class RwsResponseResult {

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
    class Status{
	def interfaceId
	SystemStatusElementEnum systemStatus
	MessageElementEnum message
	def requestId
	def requests
    }
}
