package com.happinesea.ec.rws.lib;

import java.lang.reflect.Field

import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.lang.ArrayUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.util.ClassUtils
import com.happinesea.ec.rws.lib.util.EnumUtils

import groovy.json.JsonSlurper
import groovy.util.logging.Log4j2
import groovy.util.slurpersupport.GPathResult

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
    public <R> R parse(GPathResult node, Class<R> clz) {

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

	String name = node.name()
	if(log.isDebugEnabled()) {
	    log.debug('fieldMap->{}', fieldMap)
	    log.debug('node name: {}', name)
	}

	node.children().each{ v->
	    Field f = fieldMap[v.name()]

	    if(log.isDebugEnabled()) {
		log.debug('{}-> child: {} target field: {}', name, v.name(), f?.getName())
	    }

	    if(f== null) {
		return
	    }


	    f.setAccessible(true)
	    if(ClassUtils.isPrimitveAndString(f.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('Set element: {}', v.name())
		}
		BeanUtils.copyProperty(result, f.getName(),v.text())
	    }else if(ClassUtils.isApiResponseEnum(f.getType())) {
		f.set(result, EnumUtils.getApiResponseEnum(f.getType(), v.text()))
	    }else if(ClassUtils.isTargetInterface(f.getType(), Collection)) {
		GPathResult tmp = v.children()
		if(tmp != null && !tmp.isEmpty()) {
		    List elements = []
		    Class elementType = ClassUtils.getFieldGenertics(f)
		    v.children().each {
			if(log.isDebugEnabled()) {
			    log.debug('element parse -> {}/{}', it, elementType)
			}
			elements.add(parse(it, elementType))
		    }
		    f.set(result, elements)
		}
	    }else {
		if(log.isDebugEnabled()) {
		    log.debug('Recursive element: {}', v.name())
		}
		f.set(result, parse(v, f.getType()))
	    }
	}


	return result;
    }
}
