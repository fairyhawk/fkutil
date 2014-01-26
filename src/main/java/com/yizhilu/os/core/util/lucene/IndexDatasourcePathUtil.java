package com.yizhilu.os.core.util.lucene;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName  IndexDatasourcePathUtil
 * @package com.yizhilu.os.core.util.lucene
 * @description
 * @author  liuqinggang
 * @Create Date: 2013-11-30 下午2:03:28
 * 
 */
public class IndexDatasourcePathUtil {


    private IndexDatasourcePathUtil() {
    }

    public static Map<String, String[]> getAlertEmail(
            String type, String keyword) {
        Properties properties = new Properties();
        try {
            properties.load(IndexDatasourcePathUtil.class
                    .getResourceAsStream("/lucene.properties"));
        } catch (IOException e) {
            logger.error("lucene配置文件路径不对,请检查lucene.properties是否存在");
            throw new RuntimeException(
                    "lucene配置文件路径不对,请检查lucene.properties是否存在");
        }
        synchronized (IndexDatasourcePathUtil.class) {
            Map<String, String[]> alermail = new HashMap<String, String[]>();
            alermail.put("alertaddress", properties.getProperty("alertemail")
                    .split(","));
            String[] emailparams = new String[] { new Date().toString(),
                     type, keyword };
            alermail.put("emailparams", emailparams);
            return alermail;
        }

    }

    private static IndexDatasourcePathUtil instance = null;

    public static IndexDatasourcePathUtil getInstance() {
        if (instance == null) {
            instance = new IndexDatasourcePathUtil();
        }
        return instance;
    }

    private static Logger logger = Logger
            .getLogger(IndexDatasourcePathUtil.class);

    /**
     * 
     * @param tomcathome
     *            tomcat安装路径
     * @param city
     * @param type
     * @return 索引文件目录路径(主)
     */
    public static String getIndexDir( String type) {
        Properties properties = new Properties();
        try {
            properties.load(IndexDatasourcePathUtil.class
                    .getResourceAsStream("/lucene.properties"));
        } catch (IOException e) {
            logger.error("lucene配置文件路径不对,请检查lucene.properties是否存在");
            throw new RuntimeException(
                    "lucene配置文件路径不对,请检查lucene.properties是否存在");
        }
        String indexDir = null;
        // FIXME
        synchronized (IndexDatasourcePathUtil.class) {
            indexDir = MessageFormat.format(properties.getProperty("luceneIndexDir"), type);
        }
        logger.info("lucene索引文件主路径"+",type:"+type+",indexDir:" + indexDir);
        return indexDir;
    }
    
    public static String getSpellCheckerIndexDir() {
        Properties properties = new Properties();
        try {
            properties.load(IndexDatasourcePathUtil.class
                    .getResourceAsStream("/lucene.properties"));
        } catch (IOException e) {

            logger.error("lucene配置文件路径不对,请检查lucene.properties是否存在");
            throw new RuntimeException(
                    "lucene配置文件路径不对,请检查lucene.properties是否存在");
        }
        String indexDir = null;
        synchronized (IndexDatasourcePathUtil.class) {
            indexDir = properties
                    .getProperty("spellcheckerIndexDir");
        }

        logger.info("seo spellchecker index 文件主路径=" + indexDir);
        return indexDir;
    }
    /**
     * 
     * @param tomcathome
     * @param city
     * @param type
     * @return 索引备份目录
     */
    public static String getIndexDirBak(String type) {
        Properties properties = new Properties();
        try {
            properties.load(IndexDatasourcePathUtil.class
                    .getResourceAsStream("/lucene.properties"));
        } catch (IOException e) {
            logger.error("lucene配置文件路径不对,请检查lucene.properties是否存在");
            throw new RuntimeException(
                    "lucene配置文件路径不对,请检查lucene.properties是否存在");
        }
        String indexDir = null;
        // FIXME
        synchronized (IndexDatasourcePathUtil.class) {
            indexDir = MessageFormat.format(properties
                    .getProperty("luceneIndexDirBak"), type);
        }
        logger.info(" luceneIndexDirBak lucene索引文件备份路径=" + indexDir);
        return indexDir;
    }
   
}
