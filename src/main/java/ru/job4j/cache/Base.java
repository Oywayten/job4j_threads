package ru.job4j.cache;

/**
 * Базовая модель данных
 */
public class Base {
    /**
     * уникальный идентификатор. В системе будет только один объект с таким ID
     */
    private final int id;
    /**
     * определяет достоверность версии в кеше.
     */
    private final int version;
    /**
     * Наименование бизнес-модели
     */
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}