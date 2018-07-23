package com.happinesea.ec.rws.lib;

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.util.ClassUtils
import com.happinesea.ec.rws.lib.util.EnumUtils
import com.happinesea.ec.rws.lib.util.StringUtils

import groovy.json.JsonSlurper
import groovy.util.logging.Log4j2

/**
 * あらゆるJSONをオブジェクトに変換するパーサー。<br>
 * 
 */
@Log4j2
public class RwsResponseJsonParser implements RwsResponseParser {
    /**
     * ルートノード
     */
    private def rootNode

    /**
     * Jsonレスポンス結果をパース<br>
     */
    @Override
    public <R extends ApiResponseNode> R parse(String content, Class<R> clz) {
	if(content == null || clz == null) {
	    throw new IllegalArgumentException('Content or clz is null.')
	}

	try {
	    rootNode = new JsonSlurper().parseText(content.trim())
	}catch(Exception e) {
	    if(log.isDebugEnabled()) {
		log.debug('Error content: ' + content, e)
	    }
	    throw new RuntimeException(e)
	}

	if(log.isDebugEnabled()) {
	    log.debug('rootNode : {}', rootNode.toString())
	    log.debug('clz : {}', clz.getName())
	}
	return parse(rootNode, clz)
    }

    /**
     * パース処理を行う<br>
     * 
     * @param node パース対象のノード
     * @param clz パース結果のクラス
     * @return パースした結果
     */
    public <R> R parse(Map node, Class<R> clz) {

	if(node == null) {
	    return null
	}

	def result = clz.getDeclaredConstructor().newInstance()

	if(log.isDebugEnabled()) {
	    log.debug('clz type: {} result type == clz+ {}', clz, result.getClass() == clz)
	}

	Field[] fs = ClassUtils.getFieldsApiResponse(clz)

	if(ArrayUtils.isEmpty(fs)) {
	    return result
	}
	Map<String, Field> fieldMap = new HashMap()

	for(Field f: fs) {
	    def name = f.getName()
	    fieldMap.put(name, f)
	    log.debug('target field: {}/{}', name, f.getType())
	}

	if(log.isDebugEnabled()) {
	    log.debug('fieldMap->{}', fieldMap)
	    log.debug('node name: {}', node.keySet())
	}
	for(Map.Entry entry: node.entrySet()) {
	    def key = entry.key
	    def value = entry.value
	    Field f = fieldMap[key]

	    if(log.isDebugEnabled()) {
		log.debug('{}-> child: {} target field: {}', node, key, f?.getName())
	    }

	    if(f== null) {
		continue
	    }


	    f.setAccessible(true)
	    if(ClassUtils.isPrimitveAndString(f.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('Set element: {}', key)
		}
		result[StringUtils.changeFirstCharToLower(f.getName())] = value
	    }else if(ClassUtils.isApiResponseEnum(f.getType())) {
		f.set(result, EnumUtils.getApiResponseEnum(f.getType(), value))
	    }else if(ClassUtils.isTargetInterface(f.getType(), Collection)) {
		if(value instanceof List) {
		    List tmp = new ArrayList()
		    for(Object v: (List)value) {
			if(ClassUtils.isPrimitveAndString(ClassUtils.getFieldGenertics(f))) {
			    tmp.add(v)
			}else {
			    tmp.add(parse(v, ClassUtils.getFieldGenertics(f)))
			}
		    }
		    result[StringUtils.changeFirstCharToLower(f.getName())] = tmp
		}else {
		    Map tmp = value
		    if(tmp != null && !tmp.isEmpty()) {
			List elements = []
			Class elementType = ClassUtils.getFieldGenertics(f)
			for(Map.Entry child: tmp.entrySet()) {
			    if(log.isDebugEnabled()) {
				log.debug('element parse -> {}/{}', child, elementType)
			    }
			    elements.add(parse(child, elementType))
			}
			f.set(result, elements)
		    }
		}
	    }else {
		if(log.isDebugEnabled()) {
		    log.debug('Recursive element: {}', value)
		}
		f.set(result, parse(value, f.getType()))
	    }
	}


	return result;
    }
}
