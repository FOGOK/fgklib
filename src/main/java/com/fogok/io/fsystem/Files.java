package com.fogok.io.fsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static com.fogok.io.logging.OurLogging.debug;

public class Files {

    //region Singleton
    private static Files files;
    public static Files getInstance(){
        if (files == null)
            files = new Files();
        return files;
    }
    //endregion

    /**
     * Создаём или берём нужный файл
     * @param path путь к файлу
     * @param name имя файла
     * @return файл
     */
    public File crwFile(String path, String name) throws IOException {

        File file = null;
        try {
            file = new File(path, name);
            if (file.getParentFile().mkdirs())
                    debug("Missing folders created success");
            if (file.createNewFile())
                debug("Missing file created success");

            debug(String.format("Catch file success in %s path", file.getAbsolutePath()));
            return file;

        } catch (IOException e) {
            throw new IOException(file.getAbsolutePath(), e.getCause());
        }
    }

    /**
     * Создаём или берём нужный файл
     * @param path путь к файлу относительно текущего рабочего каталога пользователя (чаще всего тот каталог, где расположен исполняемый файл вашей программы).
     * @param name имя файла
     * @return файл
     */
    public File crwFileInternal(final String path, final String name) throws IOException {
        return crwFile(System.getProperty("user.dir") + File.separator + path, name);
    }

    public void clearFileContent(File file) throws IOException {
        FileWriter fwOb = new FileWriter(file, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

}
