package ru.job4j.cash;

import java.util.Objects;

public final class Account {
    private final int id;
    private final int amount;

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int id() {
        return id;
    }

    public int amount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
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
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "Account["
                + "id=" + id + ", "
                + "amount=" + amount + ']';
    }


}