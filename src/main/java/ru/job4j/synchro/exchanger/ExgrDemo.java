package ru.job4j.synchro.exchanger;

import java.util.concurrent.Exchanger;

/**
 * Пример применения класс Exchanger.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class ExgrDemo {
    public static void main(String[] args) {
        Exchanger<String> exgr = new Exchanger<>();
        new UseString(exgr);
        new MakeString(exgr);
    }
}

/**
 * Поток типа Thread формирует символьную строку.
 */
class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;

    MakeString(Exchanger<String> c) {
        ex = c;
        str = "";
        new Thread(this).start();
    }

    @Override
    public void run() {
        char ch = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                str += (char) ch++;
            }
            try {
                System.out.println("Отправлено " + str);
                str = ex.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class UseString implements Runnable {
    Exchanger<String> ex;
    String str;

    UseString(Exchanger<String> c) {
        ex = c;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            try {
                str = ex.exchange("");
                System.out.println("Пoлyчeнo: " + str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}