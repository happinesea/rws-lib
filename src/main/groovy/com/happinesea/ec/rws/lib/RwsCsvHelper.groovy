package com.happinesea.ec.rws.lib

import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.collections.CollectionUtils

import com.happinesea.Constant
import com.happinesea.ec.rws.lib.util.StringUtils

import groovy.util.logging.Log4j2

/**
 * オブジェクトからCSVに変換するヘルパーの親クラス
 *
 * @param <S> 変換対象オブジェクト
 */
@Log4j2
abstract class RwsCsvHelper<S> {

    /**
     * CSVファイルのキー
     */
    List<String> csvKeyList = null;

    /**
     * 1レコード、1値で対応して、オブジェクトにネストされた値のキーを格納するリスト
     */
    protected Map<String, Integer> nestValKeyStorage = null;

    /**
     * 商品のタグなど、1レコード、n値で対応して、かつ、1セルにまとめるもので、オブジェクトにネストされた値のキーを可能するリスト
     */
    protected Map<String, Integer> nestRowValKeyStorage = null;

    /**
     * {@link #nestValKeyList}にキーを追加する
     * 
     * @param key キー。<code>null</code>の場合、何もせず、そのまま{@link #nestValKeyList}を返す
     * @param column キーの値を格納するカラムの項目数
     * @return キーを追加した{@link #nestValKeyList}を返す
     */
    protected Map<String, Integer> addNestValKeyStorage(String key, Integer column) {
	if(nestValKeyStorage == null) {
	    nestValKeyStorage = new HashMap()
	}
	if(key != null && column != null) {
	    nestValKeyStorage[key] = column
	}
	return nestValKeyStorage
    }

    /**
     * {@link #nestRowValKeyList}にキーを追加する
     *
     * @param key キー。<code>null</code>の場合、何もせず、そのまま{@link #nestRowValKeyList}を返す
     * @param column キーの値を格納するカラムの項目数
     * @return キーを追加した{@link #nestRowValKeyList}を返す
     */
    protected Map<String, Integer> addNestRowValKeyStorage(String key, Integer column){
	if(nestRowValKeyStorage == null) {
	    nestRowValKeyStorage = new HashMap()
	}
	if(key != null && column != null) {
	    nestRowValKeyStorage[key] = column
	}
	return nestRowValKeyStorage
    }

    /**　TODO 利用箇所がないため 
     * 
     * @param source
     * @param result
     * @return
     protected List<String> convertNestVal(S source, List<String> result) {
     if(MapUtils.isEmpty(nestValKeyStorage)) {
     return result
     }
     for(Map.Entry<String, Integer> entry : nestValKeyStorage.entrySet()) {
     if(entry.getValue() < result.size()) {
     String[] properties = entry.key.split(".")
     def tmp = source
     for(String property: properties) {
     tmp = BeanUtils.getProperty(tmp, property)
     if(tmp == null) {
     break
     }
     }
     result[entry.getKey()] = String.valueOf(tmp)
     }
     }
     return result
     }*/

    /**
     * 
     * @param source
     * @param result
     * @return
     */
    protected List<String> convertNestRowVal(S source, List<String> result){
	return result
    }
    /*protected List<String> convertNestRowVal(S source, List<String> result) {
     if(MapUtils.isEmpty(nestRowValKeyStorage) || CollectionUtils.isEmpty(result) ) {
     return result
     }
     for(Map.Entry<String, Integer> entry : nestRowValKeyStorage.entrySet()) {
     if(entry.getValue() < result.size()) {
     String[] properties = entry.key.split("\\.")
     def tmp = source
     for(String property: properties) {
     if(tmp == null) {
     break
     }
     if(tmp instanceof Collection) {
     def tmpList = []
     for(Object obj : tmp) {
     tmpList.add(BeanUtils.getProperty(obj, property))
     }
     tmp = tmpList
     }else {
     tmp = BeanUtils.getProperty(tmp, property)
     }
     }
     String strs = ''
     if(tmp != null && tmp instanceof Collection) {
     for(int i = 0; i < tmp.size(); i++) {
     if(i != 0) {
     strs += ','
     }
     strs += tmp[i]
     }
     }
     if(tmp != null) {
     result[entry.getKey()] = String.valueOf(tmp)
     }
     }
     }
     }*/

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
		if(Constant.CSV_DUMMY_STR == key) {
		    val = ""
		}else if(key.startsWith(Constant.CSV_FIX_STR)){
		    val = key.replaceFirst(Constant.CSV_FIX_STR, "")
		}else {
		    val = StringUtils.changeNull2Empty(BeanUtils.getProperty(source, key))
		}
		result.add(val)
	    }catch(Exception e) {
		result.add("")
	    }
	}

	return convertNestRowVal(source, result)
    }

    /**
     * CSVファイルのキーを表すリストを設定する
     * 
     * @param csvKeyList keyのリスト
     */
    public void setCsvKeyList(List csvKeyList) {
	this.csvKeyList = csvKeyList
    }

    /**
     * CSVファイルのキーを表すリストを取得する
     * @return
     */
    public List<String> getCsvKeyList(){
	return csvKeyList
    }
}
