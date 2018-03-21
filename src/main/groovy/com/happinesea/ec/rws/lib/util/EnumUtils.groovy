package com.happinesea.ec.rws.lib.util

/**
 * @author loveapple
 *
 */
class ResourceUtils {
    static def getResourceByClassPath(String path) {


	for (str in System.getProperty('java.class.path').split(File.pathSeparator)) {
	    def file = new File(str, path)
	    if(file.isFile()) {
		return file
	    }
	}

	throw new IOException('not found file: ' + path)
    }
}