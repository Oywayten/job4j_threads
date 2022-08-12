package ru.job4j.linked;

public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        throw new IllegalStateException("Could not set next Node");
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        throw new IllegalStateException("Could not set value");
    }
}