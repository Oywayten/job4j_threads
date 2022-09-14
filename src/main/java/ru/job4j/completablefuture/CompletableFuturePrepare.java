package ru.job4j.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Примеры работы методов класса {@link java.util.concurrent.CompletableFuture}.
 * Created by Oywayten on 11.09.2022.
 */
public class CompletableFuturePrepare {

    public static void main(String[] args) throws Exception {
        anyOfExample();
    }

    public static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 3) {
            System.out.println("Вы: я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Мам/Пап, я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Мам/Пап, я вынес мусор");
                }
        );
    }

    public static void runAsyncExample() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future = goToTrash();
        iWork();
        System.out.println("-------------------------------------\n\n");
        future.get();
    }

    public static CompletableFuture<Void> thenRunExample() {
        return CompletableFuture.runAsync(() -> {
            CompletableFuture<Void> gtt = goToTrash();
            gtt.thenRun(() -> {
                int count = 0;
                while (count < 3) {
                    System.out.println("Сын: я мою руки");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
                System.out.println("Сын: я помыл руки");
            });
            try {
                iWork();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public static CompletableFuture<String> buyProduct(String pruduct) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Сын: Мам/Пап, я пошел в магазин");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Сын: Мам/Пап, я вернулся из магазина");
            return pruduct;
        });
    }

    public static void supplyAsyncExample() throws ExecutionException, InterruptedException {
        CompletableFuture<String> bm = buyProduct("Молоко");
        CompletableFuturePrepare.iWork();
        System.out.println("Куплено: " + bm.get());
    }

    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        CompletableFuturePrepare.iWork();
        System.out.println("Куплено: " + bm.get());
    }

    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        CompletableFuturePrepare.iWork();
        System.out.println(bm.get());
    }

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();
    }

    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", моет руки");
        });
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Ivan"), washHands("Maria"),
                washHands("Pasha"), washHands("Dasha")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", моет руки";
        });
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }
}
