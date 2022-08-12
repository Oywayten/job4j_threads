package ru.job4j.io;

/**
 * Интерфейс для сохранения контента в файл
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 12.08.2022
 */
public interface ISaveFile {
    /**
     * Метод сохраняет строку в файл
     *
     * @param s строка для сохранения в файл
     */
    void saveContent(String s);
}
