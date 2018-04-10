package com.happinesea.ec.rws.lib.bean.rakuten

import java.nio.charset.StandardCharsets

import com.happinesea.ec.rws.lib.bean.form.AbstractRwsForm


/**
 * リクエストパラメータを格納する
 * 
 * 
 *
 * @param <F>
 */
class RwsParameter<F extends AbstractRwsForm> {
    /**
     * リクエストヘッダー
     */
    RwsRequestHeaderBean header

    /**
     * リクエストパス<br>デフォルト：空文字
     */
    String path = ''

    /**
     * リクエストフォーム
     */
    F requestForm

    /**
     * リクエストURI
     */
    String requestUri

    /**
     * リクエストのQuery String
     */
    private String queryString

    /**
     * フォーム値のquery stringを取得する<br>
     * {@linkplain #queryString}が<code>null</code>の場合、{@linkplain #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @return Query Stringを戻す
     */
    String getQueryString() {
	return getQueryString(false)
    }

    /**
     * フォーム値のquery stringを取得する<br>
     * {@linkplain #queryString}が<code>null</code>の場合、{@linkplain #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @param refresh {@linkplain #requestForm}の値をもとにリフレッシュするか否かを指定する。<br>
     * <code>true</code>を指定する場合、強制的に」リフレッシュ<br>
     * <code>false</code>を指定する、かつ、{@linkplain #queryString}が<code>null</code>でない場合、{@linkplain #queryString}の値をそのまま返す
     * 
     * @return Query Stringを戻す
     */
    String getQueryString(boolean refresh) {

	if(!refresh && queryString != null) {
	    return queryString
	}

	// Query Stringを生成しなおす
	String resultStr = ''
	if(requestForm == null) {
	    return resultStr
	}
	Map params = requestForm.properties
	params.each { k,v->
	    if(k && k != 'class') {
		String kStr = URLEncoder.encode(k.toString(), StandardCharsets.UTF_8.toString()) + '='
		resultStr += kStr
		if(v) {
		    resultStr += URLEncoder.encode(v.toString(), StandardCharsets.UTF_8.toString())
		}
		resultStr += '&'
	    }
	}

	if(resultStr.endsWith('&')) {
	    resultStr = resultStr.substring(0, resultStr.length() - 1)
	}

	// 生成された結果をQueryStringに設定する
	queryString = resultStr

	return queryString
    }
}
