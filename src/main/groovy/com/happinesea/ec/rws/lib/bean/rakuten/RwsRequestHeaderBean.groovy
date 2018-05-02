package com.happinesea.ec.rws.lib.bean.rakuten

import groovyx.net.http.ContentType

/**
 * RWS(RMS WEB SERVICE) WEB API リクエストヘッダー
 * 
 * 
 *
 */
class RwsRequestHeaderBean {
    String serviceSecret
    String licenseKey
    ContentType contentType
    String acceptCharset

    /**
     * Authorization「ESA Base64({@link #serviceSecret}:{@link #licenseKey})」を取得する
     * 
     * @return 「ESA Base64({@link #serviceSecret}:{@link #licenseKey})」を戻す<br>
     * {@link #serviceSecret}と{@link #licenseKey}、何れ空の場合、<code>null</code>を戻す、
     */
    String getAuthorization() {
	if(!serviceSecret?.trim() || !licenseKey?.trim()) {
	    return null;
	}

	return "ESA " + (serviceSecret+":"+licenseKey).bytes.encodeBase64().toString()
    }
}
