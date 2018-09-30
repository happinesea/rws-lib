package com.happinesea.ec.rws.cs.bean

/**
 *Content provider config info for rws Content Service.
 */
class ContentProviderConfig {
    ListInfo listInfo
    ContentInfo contentInfo
    Integer categoryId
    Integer imgCategoryId
    String srcSite
    String copyright

    static class ListInfo{
	String targetUrl
	String recordSelect
	String titleSelect
	String contetUrlSelect
    }
    static class ContentInfo{
	List<String> contentSelect
	String dateSelect
    }
}