package ldh.common.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class H2Util {

    public static void changeSql(String file1, String file2) throws IOException {
        Stream<String> lines = Files.lines(Paths.get(file1));
        List<String> newLines = lines.collect(Collectors.toList());
        List<String> keys = new ArrayList<>();
        String tableName = "";
        int idx = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(file2))) {
            boolean skip = false;
            for(int i=0, l=newLines.size(); i<l; i++) {
                String str = newLines.get(i);
                try {
                    if (str.startsWith("ALTER TABLE") || str.startsWith("--")) continue;
                    if (str.trim().equals(";")) {
                        skip = false;
                        continue;
                    }
                    if (skip) continue;
                    String uString = str.trim().toUpperCase();
                    if (uString.startsWith("CREATE TABLE")) {
                        int e= uString.indexOf("(");
                        tableName = uString.substring(uString.indexOf("CREATE TABLE") + "CREATE TABLE".length(), e);
                    }
                    if (uString.startsWith("KEY") || uString.startsWith("UNIQUE KEY") || uString.startsWith("INDEX")
                            || uString.startsWith("UNIQUE INDEX") || uString.startsWith("FOREIGN KEY")) {
                        keys.add(str);
                        continue;
                    }
                    String newLine = str;
                    int idx1 = newLine.toUpperCase().indexOf("ENGINE=");
                    if (idx1>0) {
                        int idx2 = newLine.toUpperCase().indexOf("COMMENT=");
                        if (idx2>0) {
                            newLine = str.substring(0, idx1) + " " +  str.substring(idx2);
                        } else {
                            newLine = str.substring(0, idx1);
                        }
                        if (!newLine.contains(";")) {
                            newLine = newLine + ";";
                        }
                    }
                    int t = newLine.indexOf(",");
                    if (t>0 && (uString.startsWith("KEY") || uString.startsWith("UNIQUE KEY"))) {
                        newLine = newLine.substring(0, t);
                    }

                    if (newLine.contains("PRIMARY") && newLine.trim().endsWith(",")) {
                        newLine = newLine.trim().substring(0, newLine.trim().length()-1);
                    }

                    t = newLine.toLowerCase().indexOf("double(");
                    if (t > 0) {
                        int t1 = newLine.indexOf(")", t);
                        newLine = newLine.substring(0, t) + " double " + newLine.substring(t1+1);
                    }
                    newLine = newLine.replace("CHARACTER SET utf8", "");
                    if (newLine.trim().equals(")")) {
                        skip = true;
                        newLine = ");";
                    }
                    if (newLine.startsWith("FOREIGN KEY")) {
                        int idx2 = newLine.indexOf("ON");
                        if (idx2 > 0 ) {
                            newLine = newLine.substring(0, idx2) + ",";
                        }
                    }
                    newLine = newLine.replace("COLLATE utf8_general_ci", "");
                    writer.write(newLine);
                    writer.newLine();

                    if (keys.size() > 0){
                        for (String key : keys) {
                            if (key.startsWith("FOREIGN KEY")) {
                                int idx2 = key.indexOf("ON");
                                String s1 = key.substring(0, idx2);
                                String s = "alter table " + tableName + " add " + s1 + ";";
                                writer.write(s);
                                writer.newLine();
                                continue;
                            }
                            String addIndex = key.trim().replace("KEY", "index ");
                            if (addIndex.trim().endsWith(",")) {
                                addIndex = addIndex.substring(0, addIndex.length()-1);
                            }
                            int u = addIndex.indexOf("USING");
                            if (u > 0) {
                                addIndex = addIndex.substring(0, u);
                            }

                            u = addIndex.indexOf("COMMENT"); // cib_code
                            if (u > 0) {
                                addIndex = addIndex.substring(0, u);
                            }

                            addIndex = addIndex.replace("`", "");
                            addIndex = addIndex.replaceAll("(\\d)", "");
                            addIndex = addIndex.replaceAll("\\(\\)", "");
                            u = addIndex.indexOf("(");
                            addIndex = addIndex.substring(0, u).trim() + ++idx + " on " + tableName + addIndex.substring(u);
                            addIndex = "create " + addIndex + ";";
                            writer.write(addIndex);
                            writer.newLine();
                        }
                        keys = new ArrayList<>();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        changeSql("E:\\test\\sss.sql", "E:\\test\\sss2.sql");
        System.out.println("  KEY `idx_project_id` (`project_id`),".trim().toUpperCase().startsWith("KEY"));
    }
}
