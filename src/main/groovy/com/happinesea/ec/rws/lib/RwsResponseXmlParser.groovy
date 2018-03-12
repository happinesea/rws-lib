package com.happinesea.ec.rws.lib;

import com.happinesea.ec.rws.lib.bean.RwsResponseResult
import com.happinesea.ec.rws.lib.bean.RwsResponseResult.Status
import com.happinesea.ec.rws.lib.enumerated.SystemStatusElementEnum

import groovy.util.logging.Log4j2

/**
 * @author loveapple
 *
 */
@Log4j2
public class RwsResponseXmlParser<R extends RwsResponseResult> implements RwsResponseParser<R> {
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

	R result = new R()
	result.status = parseStatus(root)

	if(log.isDebugEnabled()) {
	    log.debug('root node: {}', root)
	}

	return result;
    }

    Status parseStatus(Node root) {
	Status status = new Status()
	Node s = root.status
	if(s == null) {
	    return s
	}


	status.interfaceId = s.interfaceId
	status.systemStatus = s.systemStatus
	// TODO
	if(SystemStatusElementEnum.OK.getId() == s.systemStatus) {
	    status.systemStatus = SystemStatusElementEnum.OK
	}else {
	    status.systemStatus = SystemStatusElementEnum.NG
	}
	status.message = s.message
	status.requestId = s.requestId
    }
}
