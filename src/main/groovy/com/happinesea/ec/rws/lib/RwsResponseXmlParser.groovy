package com.happinesea.ec.rws.lib;

import java.lang.reflect.Field

import groovy.util.logging.Log4j2

/**
 * @author loveapple
 *
 */
@Log4j2
public class RwsResponseXmlParser implements RwsResponseParser {
    /**
     * ルートノード
     */
    private def rootNode

    /**
     * XMLレスポンス結果をパース<br>
     * パース仕様：<br>
     * 1．XMLノード名称をもとに、マッチするObjectの属性に値を代入する<br>
     * 2．Objectの属性が{@linkplain String}の場合、ノードの値を設定する<br>
     * 3．Objectの属性がObjectの場合、型の属性に合わせて、再帰的にパースを行う<br>
     * <br>
     * 例：
     * <pre>
     * &lt;result&gt;
     * 	&lt;status&gt;OK&lt;/status&gt;
     * 	&lt;sup&gt;
     * 	 &lt;node&gt;n1&lt;/node&gt;
     * 	&lt;/sup&gt;
     * &lt;/result&gt;
     * </pre>
     * 上記のXMLに対応するオブジェクトのクラス定義：<br>
     * <pre>
     * class Obj {	
     *  String result
     *  String status
     *  Sup sup
     *  
     * }
     * 
     * class Sup {
     *  def node
     * }
     * </pre>
     */
    @Override
    public <R> R parse(InputStream is, Class<R> clz) {
	if(is == null) {
	    throw new IllegalArgumentException('Response xml inputstream is null')
	}
	rootNode = new XmlSlurper().parse(is)

	List<Node> children = rootNode.getDirectChildren()
	R result = new R()
	if(log.isDebugEnabled()) {
	    for(Field field: clz.getDeclaredFields()) {
		field.setAccessible(true)
		log.debug('fields -> {}/{}', field.getName(),field.getType().getName())
	    }
	    log.debug('xml root name-> {}', rootNode.name())
	    log.debug('xml root result-> {}', children)
	    log.debug('result type: {}', R.class)
	    log.debug('result properties: {}', result.getClass())
	}


	if(!children) {
	    return result
	}

	result = parseRoot(children, result)

	if(log.isDebugEnabled()) {
	    log.debug('root node: {}', rootNode.get('result'))
	}

	return result;
    }
}
