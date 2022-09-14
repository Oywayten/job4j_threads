package ru.job4j.exercises.multithreading;

public class MySharedObject {
    public static MySharedObject sharedInstance = new MySharedObject();
    public Integer object2 = 22;
    public Integer object4 = 44;
    public long member1 = 12345;
    public long member2 = 6789;
}
