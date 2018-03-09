package com.happinesea.ec.rws.lib.bean

import groovyx.net.http.ContentType

/**
 * RWS(RMS WEB SERVICE) WEB API リクエストヘッダー
 * 
 * @author loveapple
 *
 */
class RwsRequestHeaderBean {
    String serviceSecret
    String licenseKey
    ContentType contentType
    String acceptCharset

    /**
     * Authorization「ESA Base64({@linkplain #serviceSecret}:{@linkplain #licenseKey})」を取得する
     * 
     * @return 「ESA Base64({@linkplain #serviceSecret}:{@linkplain #licenseKey})」を戻す<br>
     * {@linkplain #serviceSecret}と{@linkplain #licenseKey}、何れ空の場合、<code>null</code>を戻す、
     */
    String getAuthorization() {
	if(!serviceSecret?.trim() || !licenseKey?.trim()) {
	    return null;
	}

	return "ESA " + (serviceSecret+":"+licenseKey).bytes.encodeBase64().toString()
    }
}
