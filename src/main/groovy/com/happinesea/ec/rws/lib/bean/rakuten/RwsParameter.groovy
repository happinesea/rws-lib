package com.happinesea.ec.rws.lib.bean.rakuten

import java.lang.reflect.Field
import java.nio.charset.StandardCharsets

import com.happinesea.HappineseaConfig
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.form.RwsBaseForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter.HttpMethod
import com.happinesea.ec.rws.lib.util.ClassUtils

import groovy.json.JsonBuilder
import groovy.util.logging.Log4j2


/**
 * リクエストパラメータを格納する
 * 
 * 
 *
 * @param <F>
 */
@Log4j2
class RwsParameter<F extends RwsBaseForm> {
    /**
     * 通信メソッドの列挙
     */
    enum HttpMethod{
	/**
	 * GETメソッド
	 */
	GET
	/**
	 * POSTメソッド(XML body)
	 */
	, XML_POST
	/**
	 * HEADE
	 */
	, HEAD
	, JSON_POST
    }
    /**
     * 通信メソッド
     */
    HttpMethod httpMethod = HttpMethod.GET
    /**
     * 設定情報
     */
    private HappineseaConfig config = HappineseaConfig.getInstance()
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
    private F requestForm

    /**
     * リクエストURI
     */
    String requestUri

    /**
     * リクエストのQuery String
     */
    private String queryString

    /**
     * フォームオブジェクトのXML
     */
    private String xmlString

    /**
     * フォームオブジェクトのJSON
     */
    private String jsonString

    /**
     * フォーム値のquery stringを取得する<br>
     * {@link #queryString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @return Query Stringを戻す
     */
    String getQueryString() {
	return getQueryString(false)
    }

    /**
     * フォームオブジェクトをXML文字列を取得する<br>
     * {@link #xmlString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @return xmlの文字列を戻す
     */
    String getXmlString() {
	return getXmlString(false)
    }

    /**
     * フォームオブジェクトのXMLを文字列として取得する<br>
     * {@link #xmlString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @param refresh {@link #requestForm}の値をもとにリフレッシュするか否かを指定する。<br>
     * <code>true</code>を指定する場合、強制的に」リフレッシュ<br>
     * <code>false</code>を指定する、かつ、{@link #xmlString}が<code>null</code>でない場合、{@link #xmlString}の値をそのまま返す
     * 
     * @return xmlの文字列を戻す
     */
    String getXmlString(boolean refresh) {

	if(!refresh && xmlString != null) {
	    return xmlString
	}

	if(requestForm == null) {
	    return ''
	}

	// XML Stringを生成しなおす

	StringWriter result = new StringWriter()
	result.append("<?xml version=\"1.0\" encoding=\"${config.defaultEncode}\"?>")
	result.append("<request>")
	Field[] fields = ClassUtils.getFieldsApiResponse(requestForm.getClass())
	if(log.isDebugEnabled()) {
	    log.debug('request form: {}', fields)
	}
	for(Field field in fields) {
	    encodeXmlFromObj(field, requestForm, result)
	}

	result.append("</request>")

	xmlString = result.toString()

	return xmlString
    }

    /**
     * オブジェクトから、再帰的にXMLに変換する
     * 
     * @param field
     * @param obj
     * @param sw
     */
    private void encodeXmlFromObj(Field field, Object obj, StringWriter sw) {
	field.setAccessible(true)
	if(log.isDebugEnabled()) {
	    log.debug('encodeXmlFromObj -> {}', field)
	}
	Object val = field.get(obj)
	if(val == null) {
	}else if(ClassUtils.isPrimitveAndString(field.getType())) {
	    if(log.isDebugEnabled()) {
		log.debug("test content -> <${field.name}>${field.get(obj)}</${field.name}>")
	    }
	    sw.append("<${field.name}>${field.get(obj)}</${field.name}>")

	}else if(ClassUtils.isApiResponseNode(field.getType())) {
	    if(log.isDebugEnabled()) {
		log.debug("test content is node")
	    }
	    sw.append("<${field.name}>")
	    Field[] fields = ClassUtils.getFieldsApiResponse(field.get(obj).getClass())
	    for(Field f in fields) {
		encodeXmlFromObj(f, val, sw)
	    }
	    sw.append("</${field.name}>")
	}else if(val instanceof Collection) {
	    if(log.isDebugEnabled()) {
		log.debug("test content is List")
	    }
	    sw.append("<${field.name}>")
	    Collection valList = (Collection)val
	    for(Object o in valList) {
		Field[] fields = ClassUtils.getFieldsApiResponse(o.getClass())
		String name = field.name.replaceAll(/s$/, '')
		sw.append("<${name}>")
		for(Field f in fields) {
		    encodeXmlFromObj(f, o, sw)
		}
		sw.append("</${name}>")
	    }
	    sw.append("</${field.name}>")
	}else {
	    if(log.isDebugEnabled()) {
		log.debug("test content other {}", val)
	    }

	}
    }

    /**
     * フォームオブジェクトをXML文字列を取得する<br>
     * {@link #jsonString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @return jsonの文字列を戻す
     */
    String getJsonString() {
	return getJsonString(false)
    }

    /**
     * フォーム値のquery stringを取得する<br>
     * {@link #queryString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @param refresh {@link #requestForm}の値をもとにリフレッシュするか否かを指定する。<br>
     * <code>true</code>を指定する場合、強制的に」リフレッシュ<br>
     * <code>false</code>を指定する、かつ、{@link #queryString}が<code>null</code>でない場合、{@link #queryString}の値をそのまま返す
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

    /**
     * フォームオブジェクトのJSONを文字列として取得する<br>
     * {@link #jsonString}が<code>null</code>の場合、{@link #requestForm}の値をもとに、新たに生成したものを取得する
     * 
     * @param refresh {@link #requestForm}の値をもとにリフレッシュするか否かを指定する。<br>
     * <code>true</code>を指定する場合、強制的に」リフレッシュ<br>
     * <code>false</code>を指定する、かつ、{@link #jsonString}が<code>null</code>でない場合、{@link #jsonString}の値をそのまま返す
     * 
     * @return xmlの文字列を戻す
     */
    String getJsonString(boolean refresh) {
	if(!refresh && jsonString != null) {
	    return jsonString
	}
	if(requestForm == null) {
	    return ''
	}
	def builder = new JsonBuilder(requestForm)

	jsonString = builder.toString()
	return jsonString
    }
}
