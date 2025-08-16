package site.wetsion.framework.spanner.datasource;

/**
 * 分表工具类
 * @author wetsion
 */
public class DivideTableUtil {

    /**
     * 获取分表名 - 简单版本，按数量取模
     * @param tableNamePrefix 表名前缀
     * @param id 分片 id
     * @param tableCount 表数量
     * @return 分表名
     */
    public static String getTableName(String tableNamePrefix, int id, int tableCount) {
        return tableNamePrefix + "_" + (id % tableCount);
    }

    /**
     * 获取分表名 - 位运算优化版本，分表数量必须是2的幂次方
     * @param tableNamePrefix 表名前缀
     * @param id 分片 id
     * @param tableCount 表数量
     * @return 分表名
     */
    public static String getTableNameV2(String tableNamePrefix, int id, int tableCount) {
        return tableNamePrefix + "_" + (id & (tableCount - 1));
    }
}
