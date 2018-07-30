package com.happinesea.ec.rws.lib.util;

import java.lang.reflect.Field

import org.apache.commons.lang.ArrayUtils
import org.apache.commons.lang.StringUtils

import com.happinesea.ec.rws.lib.bean.ApiResponseNode
import com.happinesea.ec.rws.lib.bean.rakuten.enumerated.ApiResponseEnum

import groovy.util.logging.Log4j2

/**
 * Class操作関連のUtils
 * 
 */
@Log4j2
public class ClassUtils extends org.apache.commons.lang.ClassUtils {

    /**
     * BEANを格納するルートパッケージ
     */
    private static final String BEAN_PACKAGE_NAME = 'com/happinesea/ec/rws/lib/bean'

    /**
     * デフォルト除外するメソッドの接頭辞
     */
    private static final String NO_TARGET_METHOD_FIX = '_'

    /**
     * 基本型のラッパーを格納するセット
     */
    private static final Set<Class> PRIMITIVE_WARPPER;
    static {
	PRIMITIVE_WARPPER = new HashSet()
	PRIMITIVE_WARPPER.add(Boolean)
	PRIMITIVE_WARPPER.add(Character)
	PRIMITIVE_WARPPER.add(Byte)
	PRIMITIVE_WARPPER.add(Short)
	PRIMITIVE_WARPPER.add(Integer)
	PRIMITIVE_WARPPER.add(Long)
	PRIMITIVE_WARPPER.add(Float)
	PRIMITIVE_WARPPER.add(Double)
    }

    /**
     * 親クラスの定義を含めて、{@link ApiResponse}型、および、プリミティブ型の{@link Field}を結果に戻す。
     * 
     * @param clz 対象クラス
     * @return {@link Field}の配列を戻す。{@link ApiResponse}型がない場合、空の配列を戻す
     */
    public static Field[] getFieldsApiResponse(Class clz) {
	if(clz == null) {
	    throw new IllegalArgumentException('clz is null.')
	}

	Field[] fields = clz.getDeclaredFields()

	Field[] result = []

	for(Field field : fields) {

	    if(log.isDebugEnabled()) {
		log.debug('Field type : {} -> field: {}',
			field.getType(),
			field.getName())
	    }

	    if(isApiResponseEnum(field.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('add enum [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(isApiResponseNode(field.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('add api response node [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(isPrimitveAndString(field.getType())) {
		if(log.isDebugEnabled()) {
		    log.debug('add primitive(and String) [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }else if(isTargetInterface(field.getType(), Collection)) {
		if(log.isDebugEnabled()) {
		    log.debug('add collection [{}] to result.', field)
		}
		result = ArrayUtils.add(result, field)
	    }
	}

	if(clz.getSuperclass() != null ) {
	    Field[] tmp = getFieldsApiResponse(clz.getSuperclass())
	    if(!ArrayUtils.isEmpty(tmp)) {
		for(Field f : tmp) {
		    result = ArrayUtils.add(result, f)
		}
	    }
	}

	return result
    }

    /**
     * {@link ApiResponse}の実装クラスかどうかを判定する
     * 
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isApiResponseNode(Class clz) {

	return isTargetInterface(clz, ApiResponseNode)
    }

    /**
     * <s>{@link ApiResponseEnum}の実装クラスかどうかを判定する</s>
     *
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isApiResponseEnum(Class clz) {
	if(clz == null) {
	    return false
	}

	return isTargetInterface(clz, ApiResponseEnum)
    }

    /**
     * {@link String}、又は、プリミティブ型のラッパークラスかどうかを判定する
     * 
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isPrimitveAndString(Class clz) {
	if(clz == null) {
	    return false
	}

	if(String.class == clz ) {
	    return true
	}

	return isPrimitve(clz)
    }

    /**
     * プリミティブ型のラッパークラスかどうかを判定する
     * 
     * @param clz 対象クラス
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isPrimitve(Class clz) {
	if(clz == null) {
	    return false
	}

	return (PRIMITIVE_WARPPER.contains(clz)) && !clz.getName().startsWith(NO_TARGET_METHOD_FIX)
    }

    /**
     * 対象のIFの実装クラスかどうかを判定する
     * 
     * @param clz 判定クラス
     * @param targetIf 対象のIF
     * @return 判定結果。<code>null</code>の場合、<code>false</code>を戻す
     */
    public static boolean isTargetInterface(Class clz, Class targetIf) {
	if(clz == null || targetIf == null || ArrayUtils.isEmpty(clz.getInterfaces())) {
	    return false;
	}

	return ArrayUtils.contains(clz.getInterfaces(), targetIf)
    }

    /**
     * 対象{@link Field}のジェネクスの型を取得する<br>
     * ジェネクスが定義されないものは自分自身の型を戻す
     * 
     * @param field
     * @return
     */
    public static Class getFieldGenertics(Field field) {
	if(field == null) {
	    throw new IllegalArgumentException('field is null.')
	}

	if(ClassUtils.isPrimitveAndString(field.getType())) {
	    return field.getType()
	}
	String typeName = field.getGenericType().getTypeName()

	int startPoint = typeName.indexOf('<')
	if(startPoint < 0) {
	    return field.getType()
	}

	int endPoint = typeName.indexOf('>')
	if(endPoint < 0) {
	    throw new RuntimeException(String.format('invalid class name of [{}]', typeName))
	}

	String targetName = typeName.substring(startPoint + 1, endPoint)

	if(targetName == '?') {
	    return field.getType()
	}else {
	    Class.forName(targetName)
	}
    }

    /**
     * クラスのジェネリック定義のリストを取得する
     * 
     * @param clz 対象となるクラス
     * @return 取得結果。ジェネリックを定義しない場合、自分自身を返す
     */
    public static List<Class> getClassesByGenericSignature(Class clz) {
	if(clz == null ) {
	    return null
	}

	String typeName = clz.getGenericSignature0()
	if(StringUtils.isEmpty(typeName)) {
	    return null
	}

	int startPoint = typeName.indexOf('<')

	int endPoint = typeName.indexOf('>')
	if(endPoint < 0) {
	    throw new RuntimeException(String.format('invalid class name of [{}]', typeName))
	}

	String targetName = typeName.substring(startPoint + 1, endPoint)
	String[] types = targetName.split(';')
	List<Class> result = []
	for(String type : types) {
	    if(StringUtils.isEmpty(type)) {
		continue
	    }
	    result << getBeanClassByName(type.substring(0, type.indexOf(':')))
	}

	return result
    }

    /**
     * BEANクラスを格納するストレージ
     */
    private static Map<String, Class> beanClassStorae = new HashMap();

    /**
     * クラスローダー
     */
    private static ClassLoader classLoader = Thread.currentThread().contextClassLoader

    /**
     * ファイルシステムから対象クラス{@link #BEAN_PACKAGE_NAME ストレージ}を設定する
     * 
     * @param packageName 処理対象パッケージ
     * @param fileName ファイルパス
     */
    private static void traverseDir(String packageName, String fileName) {
	File file = new File(fileName)
	if(file.isDirectory()) {
	    file.eachFile {
		traverseDir(packageName, it.path)
	    }
	}else if(file && file.name.endsWith('.class') || file.name.endsWith('.groovy')) {
	    String classStr = replaceClasspath(packageName, file.path)
	    if(classStr) {
		Class clz = classLoader.loadClass(classStr)
		beanClassStorae.put clz.getSimpleName(), clz
	    }
	}
    }

    /**
     * jarファイルから、対象クラスを{@link #BEAN_PACKAGE_NAME ストレージ}に設定する
     * 
     * @param packageName 処理対象パッケージ
     * @param connection パッケージのURLを取得するためのコネクション
     */
    private static void findClassesWithJar(String packageName, URLConnection connection) {

	def jarFile = connection.jarFile
	try {
	    for (jarEntry in jarFile.entries()) {
		if(jarEntry.name.startsWith(packageName) && jarEntry.name.endsWith('.class')) {
		    String classStr = replaceClasspath(packageName, jarEntry.name)
		    if(classStr) {
			Class clz = classLoader.loadClass(classStr)
			beanClassStorae.put clz.getSimpleName(), clz
		    }
		}
	    }
	}finally {
	    if(jarFile) {
		jarFile.close()
	    }
	}
    }

    /**
     * パス名の文字列をクラスに変換できる文字列に変更
     * 
     * @param packageName 処理対象パッケージ
     * @param fileName 処理対象ファイルパス
     * @return 置換の結果
     */
    private static String replaceClasspath(String packageName, String fileName) {
	String fileClassPath = fileName.replace(File.separator, '/')
	int point = fileClassPath.indexOf(packageName)
	if(point < 0) {
	    return null
	}

	return fileClassPath.substring(point).replace('/', '.').replaceAll(/(\.class|\.groovy|\.java)$/, '')
    }

    /**
     * {@link #BEAN_PACKAGE_NAME}からクラスを取得する
     * 
     * @see #getBeanClassByName(String, String)
     * @param className 取得しようとするクラス名
     * @return 取得結果
     */
    public static Class getBeanClassByName(String className) {
	return getBeanClassByName(null, className)
    }

    /**
     * Beanのクラス名をもとに、指定されたパッケージから、Classを取得する。<br>
     * 指定されたクラスが存在しない場合、<code>null</code>を戻す<br>
     * 尚、同じクラス名の場合、片方しか認識できないことを注意してください。
     * 
     * @param packageName 対象パッケージ名。指定しない場合、{@link #BEAN_PACKAGE_NAME}をデフォルトとして設定する
     * @param className クラス名
     * @return 対象となるクラス
     */
    public static Class getBeanClassByName(String packageName, String className) {
	if(StringUtils.isEmpty(className)) {
	    throw new IllegalArgumentException('class name is empty.')
	}
	Class beanClz = beanClassStorae.get(className)
	if(beanClz) {
	    return beanClz
	}
	if(!packageName) {
	    packageName = BEAN_PACKAGE_NAME
	}


	URL packageUrl = classLoader.getResource(packageName)

	if(packageUrl == null) {
	    return null
	}
	switch (packageUrl.protocol) {
	    case 'file':
		traverseDir(packageName, packageUrl.getFile())
		break
	    case 'jar':
		findClassesWithJar(packageName, packageUrl.openConnection())
		break
	    default:
		return null
	}

	return beanClassStorae.get(className)
    }

    /**
     * 再帰的に、親クラスを含めて対象クラスのメソッドを取得する
     * 
     * @param clz
     * @param fieldName
     * @return
     */
    public static Field getFieldApiResponse(Class clz, String fieldName) {
	if(!clz || !fieldName) {
	    return null
	}

	Field[] fields = getFieldsApiResponse(clz)
	if(fields) {
	    for(Field f : fields) {
		if(f.getName().equals(fieldName)) {
		    return f
		}
	    }
	}

	return null
    }
}
