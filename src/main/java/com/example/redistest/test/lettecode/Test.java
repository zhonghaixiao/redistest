package com.example.redistest.test.lettecode;

import java.io.*;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws IOException {

        File file = new File("D:/workspace/cp_structure.sql");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//        Pattern pattern = Pattern.compile("`(\\s*)`(\\s*)COMMENT'(\\s*)'");
        Pattern pattern = Pattern.compile("`(\\w)`(\\s*)'(\\w)'");
//        Matcher matcher = pattern.matcher(line);
        File output = new File("D:/workspace/cp_structure_comment.sql");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output)));
        String line;
        String tempTable = "empty_table";
        while ((line = reader.readLine()) != null){
            line = line.trim();
            if (line.indexOf("CREATE TABLE") > -1){
                String[] strs = line.split(" ");
                tempTable = strs[2].replace("`", "");
                System.out.println(tempTable);
            }

            if (line.indexOf("COMMENT") > 0 && line.indexOf("ENGINE") == -1){
                System.out.println(line);
                String[] items = line.split(" ");
                String first = items[0].trim().replace("`", "");
                items = line.split("COMMENT");
                String last = items[items.length-1].replace("'", "").replace("',", "");
                System.out.println(first + "----" + last);
                writer.printf("COMMENT ON COLUMN %s.%s IS '%s';\n", tempTable, first, last);
            }
        }

        reader.close();
        writer.close();

    }

}
