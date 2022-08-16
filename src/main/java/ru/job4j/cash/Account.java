package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

@ThreadSafe
public final class Account {
    private final int id;
    @GuardedBy("this")
    private int amount;

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int id() {
        return id;
    }

    public synchronized int amount() {
        return amount;
    }

    public synchronized void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    @Override
    public synchronized boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Account) obj;
        return this.id == that.id
                && this.amount == that.amount;
    }

    @Override
    public synchronized int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public synchronized String toString() {
        return "Account["
                + "id=" + id + ", "
                + "amount=" + amount + ']';
    }


}