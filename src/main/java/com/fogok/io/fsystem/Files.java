package com.fogok.io.fsystem;

import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.esotericsoftware.minlog.Log.*;

public class Files {
    public File createOrRewriteFile(boolean absolute, final String path, String name) throws IOException {
        String sep = File.separator;

        String pathCorrect = !absolute ? System.getProperty("user.dir") + sep + path : path;

        File file = new File(pathCorrect, name);
        file.getParentFile().mkdirs();
        file.createNewFile();

        return file;
    }

    public void createLogSystem(int minlogLogLevel, String folderName) throws IOException {

        final String name;

        switch (minlogLogLevel) {
            case LEVEL_TRACE:
                name = "trace";
                break;
            case LEVEL_DEBUG:
                name = "debug";
                break;
            case LEVEL_INFO:
                name = "info";
                break;
            case LEVEL_WARN:
                name = "warn";
                break;
            case LEVEL_ERROR:
                name = "error";
                break;
            default:
                return;
        }

        File file = createOrRewriteFile(false, folderName, name +
                new SimpleDateFormat("[MM-dd-yyyy][HH-mm-ss]").format(Calendar.getInstance().getTime())
                + ".log");

        info("Log file will be save to " + file.getAbsolutePath());

        FileOutputStream fos = new FileOutputStream(file);
        TeeOutputStream myOut = new TeeOutputStream(System.out, fos);
        PrintStream printStreamToFile = new PrintStream(myOut, true);
        System.setOut(printStreamToFile);
    }
}
