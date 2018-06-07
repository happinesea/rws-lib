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
}
