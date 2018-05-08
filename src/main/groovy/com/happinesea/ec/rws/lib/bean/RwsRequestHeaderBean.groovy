package com.happinesea.ec.rws.lib.bean

import com.happinesea.ec.rws.lib.bean.enumerated.ApiTypeEnum

import groovyx.net.http.ContentType

/**
 * web service API リクエストヘッダー
 * 
 */
class RwsRequestHeaderBean {
    ApiTypeEnum apiType
    String apiKey
    String serviceSecret
    String licenseKey
    ContentType contentType
    String acceptCharset

    RwsRequestHeaderBean() {
	this(ApiTypeEnum.RAKUTEN)
    }

    RwsRequestHeaderBean(ApiTypeEnum apiType){
	this.apiType = apiType
    }

    /**
     * {@link #apiType}が{@link ApiTypeEnum#WOWMA}の場合、<br>
     * Authorization「Bearer {@link #apiKey}」を取得する<br>
     * それ以外の場合、{@link #apiType}が{@link ApiTypeEnum#RAKUTEN}とみなして、<br>
     * Authorization「ESA Base64({@link #serviceSecret}:{@link #licenseKey})」を取得する
     * 
     * @return API通信の認証情報を戻す<br>
     * {@link #apiType}が{@link ApiTypeEnum#RAKUTEN}、
     * かつ、{@link #serviceSecret}と{@link #licenseKey}、何れ空の場合、<code>null</code>を戻す、
     */
    String getAuthorization() {
	if(apiType == ApiTypeEnum.WOWMA) {
	    return "Bearer ${apiKey}"
	}else {
	    if(!serviceSecret?.trim() || !licenseKey?.trim()) {
		return null;
	    }

	    return "ESA " + "${serviceSecret}:${licenseKey}".bytes.encodeBase64().toString()
	}
    }
}
