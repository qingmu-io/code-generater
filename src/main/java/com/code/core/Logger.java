package com.code.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    public static File log;
    public static File warnLog;
    public static String sqla = "\r --" + LocalDateTime.now();

    public static void info(String sql) {
        if (log == null) {
            String pathname = "/logs/"
                    + LocalDateTime.now().toString().replace(":", "") + ".log";
            log = new File(pathname);
        }
        if (sql.contains(";"))
            sqla += "\r" + sql;
        else
            sqla += "\r" + sql + ";";
    }

    public static void warn(String sql) {
        info(sql);
    }

    public static void flush() {
        try (FileOutputStream fos = new FileOutputStream(log, true)) {
            fos.write(sqla.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
