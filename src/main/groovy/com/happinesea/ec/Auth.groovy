package com.happinesea.ec
import static groovy.json.JsonOutput.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import groovy.json.*
import groovyx.net.*
import groovyx.net.http.*

class Auth {
	def auth() {
		String targetUrl = 'https://api.rms.rakuten.co.jp'
		def http = new HTTPBuilder(targetUrl)

		http.request(POST){
			uri.path = '/es/2.0/order/searchOrder/'
			requestContentType = URLENC

			def paginationReqestModel = [requestRecordsAmount:30,requestPage:1]

			body = [dateType: '1', startDatetime: '2018-02-13T00:00:00+0900', endDatetime: '2018-02-13T00:00:00+0900',PaginationReqestModel:paginationReqestModel]

			headers.'Authorization' = "ESA " + "SP319828_I66gqrdft2BxMjRc:SL319828_cVxzynKfn1WhS1oB".bytes.encodeBase64().toString()

			headers.'Content-Type' = 'application/json; charset=utf-8'
			headers.'Accept-Charset' = 'utf-8'

			println "-->" + headers

			response.success = { resp, json ->
				println prettyPrint(toJson(json))
			}
		}
	}
}


