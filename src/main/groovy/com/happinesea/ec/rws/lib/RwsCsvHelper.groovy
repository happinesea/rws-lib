package com.happinesea.ec.rws.lib

import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.collections.CollectionUtils

import com.happinesea.Constant

/**
 * オブジェクトからCSVに変換するヘルパーの親クラス
 *
 * @param <S> 変換対象オブジェクト
 */
abstract class RwsCsvHelper<S> {

    /**
     * CSVファイルのキー
     */
    private List<String> csvKeyList = null;

    /**
     * メインの変換処理を行い、変換した結果を戻す。<br>
     * 1オブジェクトあたり、1行のCSVレコードに変換し、変換された各項目が{@link List}に格納して戻す
     * 
     * @param source 変換されるオブジェクト
     * @return 変換された1レコードを表す{@link List}
     */
    List<String> convert(S source){
	if(CollectionUtils.isEmpty(csvKeyList) || source == null) {
	    return null
	}

	List<String> result = new ArrayList()
	for(String key : csvKeyList) {
	    try {
		String val = null
		if(Constant.DUMMY_STR == key) {
		    val = ""
		}else {
		    val = BeanUtils.getProperty(source, key)
		}
		result.add(val)
	    }catch(Exception e) {
		result.add("")
	    }
	}

	return result
    }

    /**
     * CSVファイルのキーを表すリストを設定する
     * 
     * @param csvKeyList keyのリスト
     */
    public void setCsvKeyList(List csvKeyList) {
	this.csvKeyList = csvKeyList
    }
}
