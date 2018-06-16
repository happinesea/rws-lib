package com.happinesea.ec.rws.lib

import org.apache.commons.collections.CollectionUtils

import com.orangesignal.csv.CsvConfig
import com.orangesignal.csv.CsvWriter

/**
 * オブジェクトをCSVに変換するビルダー
 *
 * @param <S> 変換元オブジェクトのクラス
 */
class RwsCsvBuilder<S> {

    RwsCsvHelper<S> helper

    CsvConfig csvConfig

    public RwsCsvBuilder(RwsCsvHelper<S> helper) {
	csvConfig = new CsvConfig()
	csvConfig.setLineSeparator("\r\n")
	this.helper = helper
    }

    public String builder(List<S> source) {
	if(CollectionUtils.isEmpty(source)) {
	    return null
	}
	if(helper == null) {
	    throw new IllegalArgumentException('csv helper is null.')
	}

	final StringWriter out = new StringWriter()
	final CsvWriter writer = new CsvWriter(out, csvConfig)

	try {
	    for(S s : source) {
		List<String> recode = helper.convert(s)
		writer.writeValues(recode)
	    }
	    writer.flush()
	    return out.toString()
	}finally {
	    out.close()
	    writer.close()
	}
    }
}
