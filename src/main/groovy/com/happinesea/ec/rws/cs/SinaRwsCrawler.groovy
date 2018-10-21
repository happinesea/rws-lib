
package com.happinesea.ec.rws.cs
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.ArrayUtils
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import com.happinesea.Constant
import com.happinesea.ec.rws.cs.bean.BaiduUrlResponseBean
import com.happinesea.ec.rws.cs.bean.Content
import com.happinesea.ec.rws.cs.bean.ContentProviderConfig
import com.happinesea.ec.rws.cs.bean.MediaResponseBean
import com.happinesea.ec.rws.cs.bean.PostsResponseBean
import com.happinesea.ec.rws.cs.bean.ContentProviderConfig.ContentInfo
import com.happinesea.ec.rws.cs.bean.ContentProviderConfig.ListInfo
import com.happinesea.ec.rws.cs.bean.form.MediaPostForm
import com.happinesea.ec.rws.cs.bean.form.WpPostForm
import com.happinesea.ec.rws.lib.RwsCrawler
import com.happinesea.ec.rws.lib.RwsResponseJsonParser
import com.happinesea.ec.rws.lib.bean.RwsRequestHeaderBean
import com.happinesea.ec.rws.lib.bean.enumerated.ApiTypeEnum
import com.happinesea.ec.rws.lib.bean.form.rakuten.RwsItemApiSearchForm
import com.happinesea.ec.rws.lib.bean.rakuten.RwsParameter
import com.happinesea.ec.rws.lib.util.StringUtils

import groovy.sql.Sql
import groovy.util.logging.Log4j2
import groovyx.net.http.ContentType


@Log4j2
class SinaRwsCrawler extends RwsCrawler {
    

    public List<Content> getContentList(ContentProviderConfig cpc) {
	Document doc = Jsoup.connect(cpc.listInfo.targetUrl).get()
	Elements list = doc.select(cpc.listInfo.recordSelect)

	if(CollectionUtils.isEmpty(list) || list.isEmpty()) {
	    return null
	}

	List<Content> contents = new ArrayList()

	for(int i=0; i < list.size(); i ++) {
	    Element e = list.get(i)
	    String title = e.select(cpc.listInfo.titleSelect).html()
	    String url = e.select(cpc.listInfo.titleSelect).attr(cpc.listInfo.contetUrlSelect)

	    Content c = new Content()
	    c.title = title
	    c.url = url
	    contents.add(c)
	}

	return contents
    }
}
