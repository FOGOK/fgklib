package com.fogok.io.logging;

import com.esotericsoftware.minlog.Log;
import com.fogok.io.Fgkio;

import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.esotericsoftware.minlog.Log.*;

public class Logging {

    //region Singleton
    private static Logging logging;
    public static Logging getInstance(){
        if (logging == null)
            logging = new Logging();
        return logging;
    }
    //endregion

    private boolean isCreatedLogSystem;

    public static class LogSystemParams{

        /**
         * уровень логгирования
         */
        private int logLevel = Log.LEVEL_TRACE;

        /**
         * имя приложения, которое будет логгироваться
         */
        private String appName = "simpleName";

        /**
         * имя папки (будет создана в текущем рабочем каталоге пользователя (чаще всего тот каталог, где расположен исполняемый файл вашей программы).
         */
        private String folderName = "logs";

        /**
         * если тру, имя файла будет $appName_ + "Debug"
         */
        private boolean debug = true;

        public LogSystemParams setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public LogSystemParams setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public LogSystemParams setFolderName(String folderName) {
            this.folderName = folderName;
            return this;
        }

        public LogSystemParams setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }
    }

    /**
     * Инициализируем систему логгинга и вывод в файл со стандартными параметрами
     */
    public void createLogSystem() throws IOException {
        createLogSystem(new LogSystemParams());
    }

    /**
     * Инициализируем систему логгинга и вывод в файл
     * @param logSystemParams параметры логгирования
     */
    public void createLogSystem(LogSystemParams logSystemParams) throws IOException {

        if (isCreatedLogSystem)
            return;
        isCreatedLogSystem = true;

        Log.set(logSystemParams.logLevel);
        File file = createFile(logSystemParams);
        postProcessOnFile(logSystemParams, file);
        createTeeOutputStream(file);
    }

    private File createFile(final LogSystemParams logSystemParams) throws IOException {
        File file = Fgkio.files.crwFileInternal(logSystemParams.folderName, createFileName(logSystemParams));
        info("Log file will be save to " + file.getAbsolutePath());
        return file;
    }

    private void postProcessOnFile(final LogSystemParams logSystemParams, final File file) throws IOException {
        if (logSystemParams.debug) Fgkio.files.clearFileContent(file);
    }

    private String createFileName(final LogSystemParams logSystemParams){
        final String fullName = !logSystemParams.debug ? createLogName(logSystemParams.logLevel) + "-" + logSystemParams.appName +  "-" + new SimpleDateFormat("[MM-dd-yyyy][HH-mm-ss]").format(Calendar.getInstance().getTime()) + ".log" :
                "debug_" + logSystemParams.appName + ".log";
        return fullName;
    }

    private String createLogName(int logLevel) {
        final String logName;

        switch (logLevel) {
            case LEVEL_TRACE:
                logName = "trace";
                break;
            case LEVEL_DEBUG:
                logName = "debug";
                break;
            case LEVEL_INFO:
                logName = "debug";
                break;
            case LEVEL_WARN:
                logName = "warn";
                break;
            case LEVEL_ERROR:
                logName = "error";
                break;
            default:
                logName = "unknownLogLevel";
                break;
        }
        return logName;
    }

    /**
     * Вывод файла в два отдельных потока
     */
    private void createTeeOutputStream(File file) throws FileNotFoundException {
        TeeOutputStream myOut = new TeeOutputStream(System.out, new FileOutputStream(file));
        System.setOut(new PrintStream(myOut, true));
    }

}
