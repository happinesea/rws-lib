package com.happinesea.ec.rws.lib.util

import org.apache.commons.collections.CollectionUtils

/**
 * 文字列操作関連のUtils
 */
class StringUtils {
    /**
     * あらゆるリストを{@link String}の{@link List}に変換する。中身がnullの場合、空文字に変換する<br>
     * 尚、中では{@link String#valueOf}を使って文字列に変換するため、ラッパークラスでない、かつ、toStringを定義しない場合、<br>
     * 想定通りに文字列に変換できないかもしれません。
     * 
     * @param source 変換対象
     * @return
     */
    public static List<String> convertStringList(List source){
	if(CollectionUtils.isEmpty(source)) {
	    return null
	}

	List<String> result = new ArrayList()

	for(Object obj : source) {
	    if(obj == null) {
		result.add('')
	    }else {
		result.add(String.valueOf(obj))
	    }
	}

	return result
    }

    /**
     * nullの文字列を空文字に変換
     * 
     * @param source 対象となる文字列
     * @return 変換結果
     */
    public static String changeNull2Empty(String source) {
	return source == null ? '' : source
    }

    /**
     * 対象文字列の先頭1文字を大文字から、小文字へ変換する
     * 
     * @param source 変換対象文字列
     * @return 変換結果を戻す。空で指定される場合、指定されたものをそのまま返す
     */
    public static String changeFirstCharToLower(String source) {
	if(source) {
	    char firstChar = source.charAt(0)
	    if('A' <= firstChar && firstChar <= 'Z') {
		StringBuilder bf = new StringBuilder(source)
		bf.deleteCharAt(0)
		bf.insert(0, Character.toLowerCase(firstChar))
		return bf.toString()
	    }
	}

	return source
    }

    /* public static boolean isEqualsStringList(List<String> a1, List<String> a2) {
     boolean isEmptyA1 = CollectionUtils.isEmpty(a1)
     boolean isEmptyA2 = CollectionUtils.isEmpty(a2)
     if(isEmptyA1 && isEmptyA2) {
     return true
     }
     if(isEmptyA1 || isEmptyA2) {
     return false
     }
     if(a1.size() != a2.size()) {
     println '[' + a1.size() + ']->[' + a2.size() + ']'
     return false
     }
     for(int i = 0; i < a1.size(); i++) {
     String str1 = a1.get(i)
     String str2 = a2.get(i)
     println '[' + str1 + ']->[' + str2 + ']'
     if(str1 == null && str2 == null) {
     continue
     }
     if(str1 == null || str2 == null) {
     return false
     }
     if(!str1.equals(str2)) {
     return false
     }
     }
     return true
     }*/
    /**
     * 
     * @param target
     * @param val
     * @return
     */
    public static String cutBefor(String target, String val) {
	if(!target || !val) {
	    return target
	}
	return target.substring(target.indexOf(val) + val.length())
    }
}
