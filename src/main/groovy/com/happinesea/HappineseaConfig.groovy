package com.happinesea

/**
 * happinesea.configに定義するアプリケーションの設定情報を管理する設定情報
 * 
 * @author loveapple
 *
 */
class HappineseaConfig {
    /**
     * シングルトンインスタンス
     */
    static private HappineseaConfig instance

    /**
     * バージョンん情報<br>
     * happinesea.ec.rws.tool.version
     */
    def version

    /**
     * HTTPクライアントエージェント情報
     * happinesea.ec.rws.tool.agent
     */
    def httpClientAgent

    /**
     * ソケットタイムアウト
     */
    def socketTimeout

    /**
     * コネクションタイムアウト
     */
    def connectionTimeout

    /**
     * 設定情報を取り込み、インスタンス化を行う
     */
    private HappineseaConfig() {
	def loaderUrls = this.class.classLoader.getResource('com/happinesea/happinesea.config')
	ConfigObject conf = new ConfigSlurper().parse(loaderUrls);
	version = conf.happinesea.ec.rws.tool.version
	httpClientAgent = conf.happinesea.ec.rws.tool.agent
	socketTimeout = conf.happinesea.ec.rws.tool.socket.timeout
	connectionTimeout = conf.happinesea.ec.rws.tool.connection.timeout
    }

    /**
     * シングルトンインスタンスを取得する
     * 
     * @return {@link #instance}を戻す。ない場合、新しいインスタンスを生成する
     */
    public static HappineseaConfig getInstance() {
	if(instance == null) {
	    instance = new HappineseaConfig()
	}
	return instance
    }
}
