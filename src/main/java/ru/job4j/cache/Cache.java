package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

/**
 * Класс неблокирующего кэша на основе потокобезопасной коллекции ConcurrentHashMap
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 17.08.2022
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), checkVersion(model)) == model;
    }

    /**
     * BiFunction для сравнения версий модели и кэша.
     * Возвращает объект по результату сравнения версий.
     *
     * @param model объект для сравнения
     * @return объект типа Base
     * @throws OptimisticException, если version у модели и в кеше разные
     */
    private BiFunction<Integer, Base, Base> checkVersion(Base model) {
        return (integer, base) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return stored;

        };
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}