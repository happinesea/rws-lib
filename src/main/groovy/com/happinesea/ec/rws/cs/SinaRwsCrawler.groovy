
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

import groovy.sql.Sql
import groovy.util.logging.Log4j2

@Log4j2
class SinaRwsCrawler extends RwsCrawler {
    static final String STATUS_PUBLISH = 'publish'
    static final String STATUS_FUTURE = 'future'
    static final String STATUS_DRAFT = 'draft'
    static final String STATUS_PEDING = 'pending'
    static final String STATUS_PRIVATE = 'private'
    static String[] arv = null

    List<ContentProviderConfig> cpcs

    public SinaRwsCrawler() {
	super()
	initConfig()
    }

    void initConfig(){
	cpcs = new ArrayList()
	ContentProviderConfig international = new ContentProviderConfig()
	international.listInfo = new ListInfo()
	international.contentInfo = new ContentInfo()
	international.listInfo.targetUrl = 'https://news.sina.com.cn/world/'
	international.listInfo.recordSelect = '.news-item h2'
	international.listInfo.titleSelect = 'a'
	international.listInfo.contetUrlSelect = 'href'
	international.contentInfo.contentSelect = new ArrayList()
	international.contentInfo.contentSelect.add('#article')
	international.contentInfo.contentSelect.add('#artibody')
	//.contentInfo.dateSelect = '.date-source span'
	international.categoryId = 5
	international.srcSite = 'sina'
	cpcs.add(international)

	ContentProviderConfig fashion = new ContentProviderConfig()

	ContentProviderConfig health = new ContentProviderConfig()
	ContentProviderConfig finance = new ContentProviderConfig()
	ContentProviderConfig ent = new ContentProviderConfig()
	ContentProviderConfig sports = new ContentProviderConfig()
	ContentProviderConfig china = new ContentProviderConfig()
	china.listInfo = new ListInfo()
	china.contentInfo = new ContentInfo()
	china.listInfo.targetUrl = 'https://news.sina.com.cn/china/'
	china.listInfo.recordSelect = '.right-content li'
	china.listInfo.titleSelect = 'a'
	china.listInfo.contetUrlSelect = 'href'
	china.contentInfo.contentSelect = new ArrayList()
	china.contentInfo.contentSelect.add('#article')
	china.contentInfo.contentSelect.add('#artibody')
	//china.contentInfo.dateSelect = '.date-source span'
	china.categoryId = 11
	china.srcSite = 'sina'
	cpcs.add(china)
    }

    public MediaPostForm getImage(URL url, MediaPostForm form) {
	try {
	    HttpURLConnection conn =
		    (HttpURLConnection) url.openConnection();
	    conn.setAllowUserInteraction(false);
	    conn.setInstanceFollowRedirects(true);
	    conn.setRequestMethod("GET");
	    conn.connect();

	    int httpStatusCode = conn.getResponseCode();

	    if(httpStatusCode < HttpURLConnection.HTTP_OK || httpStatusCode > 399){
		return null
	    }

	    // Input stream
	    form.file = new DataInputStream(
		    conn.getInputStream());
	    form.mimeType = conn.getContentType()
	    return form
	}catch(Exception e) {
	    e.printStackTrace()
	    return form
	}
    }

    public File createImgDir(String srcSite) {
	String base = "/opt/asset/images/static/${srcSite}"
	Calendar cal = Calendar.getInstance()
	String dirName = "${cal.get(Calendar.YEAR)}_${cal.get(Calendar.MONTH) + 1}_${cal.get(Calendar.DATE)}"
	File dir = new File(base, dirName)
	if(!dir.isDirectory()) {
	    dir.mkdirs()
	}
	return dir
    }

    public String getHttpPath(String path) {
	String base = "/wp-content/asset/images"
	if(!path) {
	    return
	}

	String target = "/opt/asset/images"
	return path.replace(/${target}/, base)
    }

    //public String putImage1(String urlStr) {
    public static void mainTest(String[] urlStr) {

	/*header.userName = 'jc-writer@happinesea.com'
	 header.password = '3GrF nTA2 9JNP 51zN jzni rqCo' // 2MC445sH
	 parameter.header = header
	 parameter.requestUri = 'http://test2.happinesea.com'
	 //parameter.path = '/wp-json/wp/v2/media'
	 parameter.path = '/info.php'*/

	/*String baseUrl = "http://test2.happinesea.com";
	 String username = "jc-writer@happinesea.com";
	 String password = "3GrF nTA2 9JNP 51zN jzni rqCo";
	 boolean debug = false;
	 final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, username, password, true, debug));
	 final Post post = PostBuilder.aPost()
	 .withTitle(TitleBuilder.aTitle().withRendered("标题").build())
	 .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("呈现").build())
	 .withContent(ContentBuilder.aContent().withRendered("内容").build())
	 .build();
	 final Post createdPost = client.createPost(post, PostStatus.publish);*/
	SinaRwsCrawler crawler = new SinaRwsCrawler()
	println crawler.putImage("http://${SinaRwsCrawler.arv[0]}/wp-content/uploads/2018/08/8844548_banner3-1.jpeg");

    }

    public MediaResponseBean putImage(String urlStr) {
	try {
	    log.info("put image: {}", urlStr)
	    if(urlStr.startsWith("//")) {
		urlStr = "http:${urlStr}"
	    }

	    URL url = new URL(urlStr)

	    MediaPostForm form = new MediaPostForm(
		    )
	    form = getImage(url, form)
	    if(form.file == null) {
		return null
	    }
	    RwsParameter<MediaPostForm> parameter = new RwsParameter<MediaPostForm>()

	    RwsRequestHeaderBean header = new RwsRequestHeaderBean()
	    header.apiType = ApiTypeEnum.BASIC
	    header.userName = 'jc-writer@happinesea.com'
	    header.password = '3GrF nTA2 9JNP 51zN jzni rqCo' // 2MC445sH
	    parameter.header = header
	    parameter.requestUri = "http://${SinaRwsCrawler.arv[0]}"
	    parameter.path = '/wp-json/wp/v2/media'
	    //parameter.path = '/info.php'

	    form.status = STATUS_PUBLISH
	    form.title =url.file
	    parameter.requestForm = form
	    HttpResponse response = postRestRequest(parameter)

	    log.info("put media: {} -> {}", parameter.requestUri, form.title)
	    String result = EntityUtils.toString(response.entity)
	    if(result) {
		RwsResponseJsonParser parser = new RwsResponseJsonParser()
		MediaResponseBean bean = parser.parse(result, MediaResponseBean)
		log.info("put media result: {}", ToStringBuilder.reflectionToString(bean))
		return bean
	    }else {
		log.info("put media response nothing")
		return null
	    }
	}catch(Exception e) {
	    e.printStackTrace()
	    return null
	}
    }

    //TODO tag update
    public boolean updateTags(Sql db, String[] tags) {
	String selectSql = "select tags"
	return

    }


    /**
     * 1: host name
     * 
     * @param arv
     */
    public static void main(String[] arv) throws Exception {
	SinaRwsCrawler.arv = arv
	if(ArrayUtils.isEmpty(arv)) {
	    println "host name is emtpy."
	    System.exit(1)
	}

	// TMP DB登録
	def db = Sql.newInstance(
		"jdbc:mysql://${arv[0]}:3306/news?useUnicode=true&characterEncoding=utf8",          // DB接続文字列
		'news',           // ユーザ名
		'lTBltwGSHXBckQBG',         // パスワード
		'com.mysql.jdbc.Driver'  // JDBCドライバ
		)


	SinaRwsCrawler crawler = new SinaRwsCrawler()
	for(ContentProviderConfig cpc : crawler.cpcs) {
	    List<Content> contents = crawler.getContentList(cpc)

	    for(Content c : contents) {
		try {
		    Document doc = Jsoup.connect(c.url).get()
		    Element ele = null
		    for(String contentTmp : cpc.contentInfo.contentSelect) {
			ele = doc.selectFirst(contentTmp)
			if(ele && ele.html()) {
			    break
			}
		    }
		    if(!ele) {
			continue
		    }

		    // TODO 一時対応
		    String selectSql = "select * from content_tmp where url = :url"
		    List result = db.rows(selectSql, [url: c.url])
		    if(CollectionUtils.isNotEmpty(result)) {
			continue
		    }

		    c.body = ele.html()
		    c.keywords = doc.selectFirst("meta[name=\"keywords\"]").attr("content")
		    if(c.keywords) {
			c.keywords = c.keywords.replace(" ", "")
			c.keywords = c.keywords.replace("　", "")
		    }
		    // get images
		    Elements imageElements = ele.select("img")
		    if(imageElements != null && !imageElements.isEmpty()) {
			RwsCrawler imgCraw = new RwsCrawler()
			for(int i = 0; i < imageElements.size(); i++) {

			    String orgSrc = imageElements.get(i).attr("src")
			    if(!(orgSrc.startsWith("//") || orgSrc.startsWith("http"))) {
				continue
			    }
			    c.has_img_flg = true;
			    String src = orgSrc
			    try {
				// 画像取得
				if(orgSrc.startsWith("//")) {
				    src = "http:${src}"
				}
				/*URL url = new URL(src);
				 InputStream imageIs = crawler.getImage(url)
				 if(imageIs == null) {
				 continue
				 }
				 File dir = crawler.createImgDir(cpc.srcSite)
				 Path path = Paths.get(url.getFile())
				 File newFile = new File(dir.getPath(), path.getFileName().toString())
				 println "${newFile}"
				 // APIに送信　⇒　保存
				 if(!newFile.exists()) {
				 Files.copy(imageIs, newFile.toPath())
				 }
				 // 画面のURLを置き換え
				 String p = newFile.getPath()*/

				log.info( "post img ${src}")
				MediaResponseBean p = crawler.putImage(src)
				if(!c.featured_media) {
				    c.featured_media = p.id
				}

				//p = p.replace("\\", "/")
				if(crawler.getHttpPath(p.source_url)) {
				    c.body = c.body.replace(/${orgSrc}/, crawler.getHttpPath(p.source_url))
				}else {
				    log.info("${p.source_url} is empty.")
				}
			    }catch(Exception e) {
				e.printStackTrace()
				continue
			    }
			}
		    } else {
			c.has_img_flg = false;
		    }

		    // String str = doc.selectFirst(cpc.contentInfo.dateSelect.toString()).html().trim()

		    //c.last_modifierd = org.apache.http.client.utils.DateUtils.parseDate(str, new String[ "yyyy年MM月dd日 HH:mm"])
		    //c.last_modifierd = DateUtils.parseDate(str, new String[ "yyyy年MM月dd日 HH:mm"])
		    c.last_modifierd = new Date()
		    c.has_video_flg = false;


		}catch(Exception e) {
		    e.printStackTrace()
		    continue
		}
	    }
	    /**/
	    for(Content c : contents) {
		Map<String, String> params = new HashMap()
		String selectSql = "select * from content_tmp where url = :url"
		List result = db.rows(selectSql, [url: c.url])
		if(CollectionUtils.isEmpty(result)) {
		    db.executeInsert("insert into content_tmp (url, title, body, keywords, last_modifierd, date_gmt, has_img_flg, featured_media, has_video_flg, created_dt) values (?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, CURRENT_TIMESTAMP())"
			    , [c.url, c.title, c.body, c.keywords, //c.last_modifierd, //c.last_modifierd,
				c.has_img_flg ? Constant.FLAG_ON : Constant.FLAG_OFF
				, c.featured_media
				, c.has_video_flg ? Constant.FLAG_ON : Constant.FLAG_OFF,
				//new Date()
			    ])
		    List val = db.rows(selectSql, [url: c.url])
		    if(CollectionUtils.isNotEmpty(val)) {
			db.executeInsert('insert into content_tmp_term_rel (content_id, term_taxonomy_id) values (?, ?) '
				,[val[0].id , cpc.categoryId])
			if(c.has_img_flg && cpc.imgCategoryId) {
			    db.executeInsert('insert into content_tmp_term_rel (content_id, term_taxonomy_id) values (?, ?) '
				    ,[val[0].id , cpc.imgCategoryId])
			}
		    }
		}
	    }
	    // 登録
	    List targetContent = db.rows("select * from content_tmp where status = 0")
	    if(CollectionUtils.isNotEmpty(targetContent)) {
		RwsRequestHeaderBean header = new RwsRequestHeaderBean()
		header.apiType = ApiTypeEnum.BASIC
		header.userName = 'jc-writer@happinesea.com'
		header.password = '3GrF nTA2 9JNP 51zN jzni rqCo' // 2MC445sH
		RwsParameter<RwsItemApiSearchForm> parameter = new RwsParameter()
		parameter.header = header
		parameter.requestUri = "http://${arv[0]}"
		parameter.path = '/wp-json/wp/v2/posts'
		for(Object obj : targetContent) {
		    WpPostForm form = new WpPostForm()
		    form.title = obj.title
		    form.content = obj.body
		    form.status = STATUS_PUBLISH
		    form.featured_media = obj.featured_media
		    /*TODO add tags
		     * if(obj.keywords) {
		     String[] tags = obj.keywords.split(',')
		     form.tags = Arrays.asList(tags)
		     }*/

		    List targetCatgories = db.rows("select * from content_tmp_term_rel where content_id = ${obj.id}")
		    if(CollectionUtils.isNotEmpty(targetCatgories)) {
			form.categories = new ArrayList()
			for(Object cat: targetCatgories) {
			    form.categories.add(cat.term_taxonomy_id)
			}
		    }
		    if(obj.has_img_flg == Constant.FLAG_ON || obj.has_img_flg == true) {
			form.categories.add(6)
		    }
		    parameter.requestForm = form
		    HttpResponse response = crawler.postRestRequest(parameter)
		    String result = EntityUtils.toString(response.entity)
		    if(result) {
			RwsResponseJsonParser parser = new RwsResponseJsonParser()
			PostsResponseBean bean = parser.parse(result, PostsResponseBean)
			if(bean.id) {
			    db.executeUpdate("update content_tmp set status = 1 where id = ${bean.id}")
			}else {

			}
		    }else {

		    }
		}
	    }
	    /**/
	}
    }

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
