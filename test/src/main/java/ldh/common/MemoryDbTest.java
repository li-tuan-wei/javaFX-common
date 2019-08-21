package ldh.common;


import ldh.common.function.H2FunctionRegister;
import ldh.common.util.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Puhui on 2016/10/20.
 */
public class MemoryDbTest implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryDbTest.class);

    public static final AtomicBoolean IS_REGISTER = new AtomicBoolean(false);

    //    @Resource(name="jdbcTemplate")
    protected JdbcTemplate jdbcTemplate;
    @Value("${window_plot_dir}")
    private String windowPlotDir;
    @Value("${linux_plot_dir}")
    private String linuxPlotDir;

    private ApplicationContext applicationContext;

    public MemoryDbTest() {

    }

    public MemoryDbTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void register() {
        if (!IS_REGISTER.get()) {
            IS_REGISTER.compareAndSet(false, true);
            try {
                new H2FunctionRegister(this.getJdbcTemplate());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 加载项目中的sql 数据
     *
     * @param obj
     * @param fileName
     */
    public void importData(Object obj, String fileName) {
        String sqlFile = obj.getClass().getResource(fileName).getFile();
        DatabaseUtil.executeSql(getJdbcTemplate(), sqlFile);
    }

    public void importSystemData(String sqlFile) {
        ;
        DatabaseUtil.executeSql(getJdbcTemplate(), sqlFile);
    }

    public void importData(JdbcTemplate jdbcTemplate, Object obj, String fileName) {
        String sqlFile = obj.getClass().getResource(fileName).getFile();
        DatabaseUtil.executeSql(jdbcTemplate, sqlFile);
    }

    public void importSystemData(JdbcTemplate jdbcTemplate, String sqlFile) {
        DatabaseUtil.executeSql(jdbcTemplate, sqlFile);
    }

    public void exportAllTable(String fileName) {
        exportAllTable(fileName, null);
    }

    public void exportAllTable(final String fileName, String desc) {
        Map<String, Map<String, String>> databaseInfo = DatabaseUtil.getDatabaseInfo(getJdbcTemplate().getDataSource());

        int num = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        final CountDownLatch countDownLatch = new CountDownLatch(databaseInfo.size());
        final BlockingQueue<String> queue = new LinkedBlockingDeque<String>();

        try {
            deleteFile(fileName);
            writeDescToFile(fileName, desc);

            for (Map.Entry<String, Map<String, String>> entry : databaseInfo.entrySet()) {
                final String tableName = entry.getKey();
                if (isExcludeTable(tableName)) {
                    countDownLatch.countDown();
                    continue;
                }
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        String sql = exportTable(tableName);
                        if (tableName.toLowerCase().equals("loan")) {
                            System.out.println("loan===:" + sql + "," + tableName);
                        }
                        if (sql != null && !sql.trim().equals("")) {
                            queue.add(sql);
                            System.out.println("sql===:" + sql + "," + tableName);
                        }
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();

            saveFile(queue, fileName, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private void writeDescToFile(String fileName, String desc) throws Exception {
        if (desc == null || desc.trim().equals("")) return;
        saveFile("-- " + desc + "\r\n", fileName, true);
    }

    private void deleteFile(String filePath) {
        String fileURL = this.getDbPlotDir() + "/" + filePath;
        File file = new File(fileURL);
        while (file.delete()) {
        }
    }

    public String exportTable(String tableName) {
        Map<String, Map<String, String>> databaseInfo = DatabaseUtil.getDatabaseInfo(this.getJdbcTemplate().getDataSource());
        Map<String, String> tableInfo = databaseInfo.get(tableName.toUpperCase());
        if (tableInfo == null || tableInfo.size() < 1) {
            throw new RuntimeException("没有这个表");
        }
        if (isExcludeTable(tableName)) {
            return "";
        }
        String sql = DatabaseUtil.exportToSql(getJdbcTemplate(), tableName, "");
        return sql;
//        saveFile(sb.toString(), fileName, isAppend);
    }

    private void saveFile(String sb, String fileName, boolean isAppend) throws Exception {
        String file = getDbPlotDir() + "/" + fileName;
        OutputStream fis = new FileOutputStream(file, isAppend);
        OutputStreamWriter isr = new OutputStreamWriter(fis, Charset.forName("UTF-8"));
        BufferedWriter br = new BufferedWriter(isr);
        br.write(sb);
        br.close();
    }

    private void saveFile(Queue<String> queue, String fileName, boolean isAppend) throws Exception {
        String file = getDbPlotDir() + "/" + fileName;
        OutputStream fis = new FileOutputStream(file, isAppend);
        OutputStreamWriter isr = new OutputStreamWriter(fis, Charset.forName("UTF-8"));
        BufferedWriter br = new BufferedWriter(isr);
        for (String str : queue) {
            br.write(str);
        }
        br.close();
    }

    /**
     * 这些表数据不导出
     * @return
     */
    public String excludeTableName() {
        return "demo, demo1";
    }

    public boolean isExcludeTable(String tableName) {
        String[] excludeTables = excludeTableName().split(",");
        for (String excludeTable : excludeTables) {
            if (tableName.toUpperCase().equals(excludeTable.trim().toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public String getDbPlotDir() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return this.windowPlotDir;
        } else if (os.toLowerCase().startsWith("linux")) {
            return this.linuxPlotDir;
        } else {
            throw new RuntimeException("判断不出当前是哪个系统");
        }
    }

    public List<String> keyword() {
        return Arrays.asList("order");
    }

    public void reload() {
        clean();
        String file = MemoryDbTest.class.getResource("/test-schema.sql").getFile();
        importSystemData(file);

        file = MemoryDbTest.class.getResource("/test-data.sql").getFile();
        importSystemData(file);
    }

    protected void clean() {
        int num = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        try {
            Map<String, Map<String, String>> databaseInfo = DatabaseUtil.getDatabaseInfo(getJdbcTemplate().getDataSource());
            final AtomicInteger inc = new AtomicInteger(0);
            final CountDownLatch countDownLatch = new CountDownLatch(databaseInfo.size());

            for (Map.Entry<String, Map<String, String>> entry : databaseInfo.entrySet()) {
                final String tableName = entry.getKey();
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String newtableName = changeTableName(tableName);
                            String sql = "delete from " + newtableName;
                            getJdbcTemplate().execute(sql);
                            inc.incrementAndGet();
                        } catch (Exception e) {
                            LOGGER.error("delete table error!", e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
                });
            }
            countDownLatch.await();
            LOGGER.info("delet table size:{}, all table size:{} ", inc.get(), databaseInfo.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private String changeTableName(String tableName) {
        for (String kw : keyword()) {
            if (tableName.toLowerCase().equals(kw)) {
                tableName = "`" + kw + "`";
            }
        }
        return tableName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
        }
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register();
    }
}

