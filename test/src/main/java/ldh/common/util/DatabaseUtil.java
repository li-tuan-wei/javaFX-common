package ldh.common.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Puhui on 2016/10/20.
 */
public class DatabaseUtil {

    private static Map<String, Map<String, String>> databasseInfo = new HashMap<String, Map<String, String>>();

    private static volatile boolean isInit = false;

    private DatabaseUtil() {

    }

    public static Map<String, Map<String, String>> getDatabaseInfo(DataSource dataSource) {
        if (!isInit) {
            getTableNameByCon(dataSource);
            isInit = true;
        }
        return databasseInfo;
    }

    public static void executeSql(JdbcTemplate jdbcTemplate, String file) {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(file), false);
    }

    public static void executeSql(DataSource dataSource, String file) {
        try (Connection connection = dataSource.getConnection()) {
            File f = new File(file);
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(f));
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

    public static void getTableNameByCon(DataSource dataSource) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            DatabaseMetaData meta = con.getMetaData();
            ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
            while (rs.next()) {
                Map<String, String> tableInfo = new LinkedHashMap<String, String>();
                String tableName = rs.getString(3);
//                System.out.println("表名：" + tableName);
                databasseInfo.put(tableName, tableInfo);

                ResultSet colRet = meta.getColumns(null, "%", tableName, "%");
                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");
//                    String columnType = colRet.getString("TYPE_NAME");
//                    int datasize = colRet.getInt("COLUMN_SIZE");
//                    int digits = colRet.getInt("DECIMAL_DIGITS");
//                    int nullable = colRet.getInt("NULLABLE");
                    tableInfo.put(columnName, columnName);
                }
            }
            con.close();
        } catch (Exception e) {
            try {
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static String exportToSql(JdbcTemplate jdbcTemplate, String table, String where, Object...param) {
        Map databaseInfo = DatabaseUtil.getDatabaseInfo(jdbcTemplate.getDataSource());
        Map tableInfo = (Map)databaseInfo.get(table);
        if (table.equalsIgnoreCase("order")) {
            table = "`" + table + "`";
        }
        String sql = "select * from " + table + " " + where;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, param);
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> map : list) {
            String insertSql = buildSql(table, tableInfo, map);
            sb.append(insertSql).append("\r\n");
        }
        return sb.toString();
    }

    public static String buildSql(String tableName, Map<String, String> tableInfo, Map<String, Object> map) {
        String sql = "insert into " + tableName + "(";
        String values = "";
        for (Map.Entry<String, String> entry : tableInfo.entrySet()) {
            sql += entry.getKey() + ", ";
            values += getValue(map, entry.getKey()) + ", ";
        }
        values = values.substring(0, values.length() - 2);
        sql = sql.substring(0, sql.length() - 2) + ") values(" + values + ");";
        return sql;
    }

    public static Object getValue(Map<String, Object> map, String columnName) {
        Object value = map.get(columnName);
        if (value == null) return "NULL";
        return "'" + value + "'";
    }
}
