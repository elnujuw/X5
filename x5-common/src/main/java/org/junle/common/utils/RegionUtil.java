package org.junle.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.File;
import java.io.InputStream;

/**
 * 使用 ip2region.xdb 库进行 IP 地址到地理位置的离线查询
 *
 * @author elnujuw
 */
public class RegionUtil {
    private static final Logger log = LoggerFactory.getLogger(RegionUtil.class);

    private static final String JAVA_TEMP_DIR = "java.io.tmpdir";
    private static final String IP_XDB_PATH = "ip2region/ip2region.xdb";

    private static volatile Searcher searcher = null;

    static {
        initializeIpDatabase();
    }

    /**
     * 初始化 IP 数据库
     */
    private static void initializeIpDatabase() {
        try {
            // 将数据库文件加载到内存或临时目录
            String dbPath = loadDatabaseToTemp();
            if (dbPath == null) {
                log.error("Failed to load IP database file.");
                return;
            }

            // 从文件加载到内存
            byte[] cBuff = Searcher.loadContentFromFile(dbPath);
            searcher = Searcher.newWithBuffer(cBuff);
            log.info("IP region database initialized successfully with xdb.");
        } catch (Exception e) {
            log.error("Failed to initialize IP region database: {}", e.getMessage(), e);
        }
    }

    /**
     * 将 IP 数据库文件从资源路径复制到临时目录
     *
     * @return 数据库文件路径，如果加载失败则返回 null
     */
    private static String loadDatabaseToTemp() {
        try {
            ClassPathResource resource = new ClassPathResource(IP_XDB_PATH);
            if (!resource.exists()) {
                log.error("IP database resource not found in classpath: {}", IP_XDB_PATH);
                return null;
            }

            String tmpDir = System.getProperty(JAVA_TEMP_DIR);
            String dbPath = tmpDir + File.separator + "ip2region.xdb";
            File dbFile = new File(dbPath);

            try (InputStream inputStream = resource.getInputStream()) {
                FileUtils.copyInputStreamToFile(inputStream, dbFile);
            }

            log.info("IP database loaded to temporary path: {}", dbPath);
            return dbPath;
        } catch (Exception e) {
            log.error("Error loading IP database to temporary directory: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解析 IP，获取对应的地理位置信息
     *
     * @param ip 待查询的 IP 地址
     * @return 返回 IP 对应的地理位置信息，如果查询失败则返回空字符串
     */
    public static String getRegion(String ip) {
        if (searcher == null) {
            log.error("Searcher is not initialized. Cannot perform IP lookup.");
            return StringUtils.EMPTY;
        }

        if (StringUtils.isEmpty(ip)) {
            log.error("Provided IP is null or empty.");
            return StringUtils.EMPTY;
        }

        try {
            long startTime = System.currentTimeMillis();
            String region = searcher.search(ip);
            long endTime = System.currentTimeMillis();
            log.debug("Region lookup for IP [{}] took [{}] ms, result: [{}]", ip, endTime - startTime, region);
            return region;
        } catch (Exception e) {
            log.error("Error during IP region lookup: {}", e.getMessage(), e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * 清理资源，释放内存
     */
    public static void destroy() {
        if (searcher != null) {
            try {
                searcher.close();
                log.info("IP region database searcher closed successfully.");
            } catch (Exception e) {
                log.error("Error closing IP region database searcher: {}", e.getMessage(), e);
            }
        }
    }
}
