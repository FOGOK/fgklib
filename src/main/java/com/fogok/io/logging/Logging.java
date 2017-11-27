package com.fogok.io.logging;

import com.esotericsoftware.minlog.Log;
import com.fogok.io.Fgkio;

import org.apache.commons.io.output.TeeOutputStream;

import java.io.File;
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

    /**
     * Инициализируем систему логгинга и вывод в файл
     * @param minlogLogLevel уровень логгирования
     * @param appName имя приложения, которое будет логгироваться
     * @param folderName имя папки (будет создана в текущем рабочем каталоге пользователя (чаще всего тот каталог, где расположен исполняемый файл вашей программы).
     */
    public void createLogSystem(int minlogLogLevel, final String appName, final String folderName) throws IOException {

        if (isCreatedLogSystem)
            return;
        isCreatedLogSystem = true;

        final String logName;

        switch (minlogLogLevel) {
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
                return;
        }

        Log.set(minlogLogLevel);

        final String fullName = logName + "-" + appName +  "-" + new SimpleDateFormat("[MM-dd-yyyy][HH-mm-ss]").format(Calendar.getInstance().getTime()) + ".log";

        File file = Fgkio.files.crwFileInternal(folderName, fullName);

        info("Log file will be save to " + file.getAbsolutePath());

        //делаем так, чтобы лог выводился одновременно в файл и в консоль
        TeeOutputStream myOut = new TeeOutputStream(System.out, new FileOutputStream(file));    //вывод в два отдельных потока
        System.setOut(new PrintStream(myOut, true));
    }

}
