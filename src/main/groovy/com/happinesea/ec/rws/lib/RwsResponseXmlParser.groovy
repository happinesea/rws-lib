package com.happinesea.ec.rws.lib;

import com.happinesea.ec.rws.lib.bean.RwsResponseBody;

import groovy.util.logging.Log4j2

/**
 * @author loveapple
 *
 */
@Log4j2
public class RwsResponseXmlParser<R extends RwsResponseBody> implements RwsResponseParser<R> {
    /**
     * ルートノード
     */
    private Node root

    /**
     * XMLレスポンス結果をパース
     */
    @Override
    public R parse(InputStream is) {
	if(is == null) {
	    throw new IllegalArgumentException('Response xml inputstream is null')
	}
	root = new XmlParser().parse(is)

	if(log.isDebugEnabled()) {
	    log.debug('root node: {}', root)
	}

	return null;
    }
}
