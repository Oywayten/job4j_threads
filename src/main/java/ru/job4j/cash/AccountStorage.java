package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Класс реализует блокирующий кеш UserStorage для модели User
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 14.08.2022
 */
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    /**
     * Метод добавляет аккаунт в хранилище accounts
     *
     * @param account аккаунт для добавления в хранилище
     * @return true, если добавление успешно, false, если аккаунт уже существует
     * @throws NullPointerException, если переданный account == null
     */
    public synchronized boolean add(Account account) {
        Objects.requireNonNull(account);
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    /**
     * Метод обновляет указанный аккаунт
     *
     * @param account обновленный аккаунт, которым надо заменить старый аккаунт
     * @return true, если обновление успешно
     * @throws NullPointerException, если переданный account == null
     */
    public synchronized boolean update(Account account) {
        Objects.requireNonNull(account);
        return accounts.replace(account.id(), account) != null;
    }

    /**
     * Метод удаляет аккаунта по id из хранилища
     *
     * @param id id аккаунта для удаления
     * @return true, если удаление успешно
     * @throws IllegalStateException, если нет такого аккаунта
     */
    public synchronized boolean delete(int id) {
        if (getById(id).isEmpty()) {
            throw new IllegalStateException("Not found account by id = " + id);
        }
        return accounts.remove(id, accounts.get(id));
    }

    /**
     * Метод находит аккаунт по id
     *
     * @param id id аккаунта по которому осуществляется поиск в хранилище accounts
     * @return возвращает Optional<Account> с нужным аккаунтом или пустой Optional,
     * если аккаунт отсутствует в хранилище
     */
    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    /**
     * Метод переводит сумму amount со счета fromId в счет toId
     *
     * @param fromId id аккаунта, с которого надо перевести сумму
     * @param toId   id аккаунта, на который надо перевести сумму
     * @param amount сумма для перевода
     * @return true, если перевод выполнен успешно
     * @throws IllegalStateException, если недостаточно средств для перевода
     * @throws NullPointerException, если нет какого-то из аккаунтов нет в хранилище
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (accounts.get(fromId).amount() < amount) {
            throw new IllegalStateException("Not enough money by id = " + fromId);
        }
        accounts.get(fromId).setAmount(accounts.get(fromId).amount() - amount);
        accounts.get(toId).setAmount(accounts.get(toId).amount() + amount);
        return true;
    }
}