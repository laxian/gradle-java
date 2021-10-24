package org.sw.nb.demos.gradle.java;

import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main2 {

    static ExecutorService executorService;
    public static void work(int i) {
        executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    System.out.println("-------> " + sec());

                    Thread.sleep(i*1000);
                    System.out.println("耗时：" + i + "s " + sec());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(runnable);
    }

    public static void main(String[] args) {

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                System.out.println(Thread.currentThread().getName());
//                int cnt = 0;
//                for (; ; ) {
//                    Thread.sleep(1000);
//                    emitter.onNext(cnt++);
//                }
//            }
//        })
//        Observable.interval(0, 3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.from(Executors.newScheduledThreadPool(1)))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        work();
//                    }
//                });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                int i = 0;
//                for (;;) {
//                    Thread.sleep(2000);
                    emitter.onNext("hello: " + i++);
//                }
            }
        })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        int i = new Random().nextInt(5) *3;
                        work(i);
                        Thread.sleep(i*1000);
                        throw new IOException("----");
                    }
                })
                .retryWhen((Function<Observable<Throwable>, Observable<?>>) throwableFlowable -> throwableFlowable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        return Observable.timer(0, TimeUnit.SECONDS);
                    }
                }))
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
            }
        });


        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static int sec() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
}
