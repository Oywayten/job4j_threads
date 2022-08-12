package ru.job4j.io;

import java.io.*;

/**
 * Класс сохраняет контент в файл
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 12.08.2022
 */
public final class SaveFile implements ISaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    @Override
    public void saveContent(String content) {
        synchronized (this) {
            try (OutputStream o = new FileOutputStream(file);
                 BufferedOutputStream out = new BufferedOutputStream(o)) {
                for (int i = 0; i < content.length(); i += 1) {
                    out.write(content.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}