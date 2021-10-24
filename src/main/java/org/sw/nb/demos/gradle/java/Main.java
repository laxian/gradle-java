package org.sw.nb.demos.gradle.java;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                scheduler.shutdown();
                return scheduler;
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                int cnt = 0;
                for (; ; ) {
                    Thread.sleep(1000);
                    emitter.onNext(cnt++);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " " + integer);
                    }
                });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                int cnt = 0;
                for (; ; ) {
                    Thread.sleep(1000);
                    emitter.onNext(cnt++);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " " + integer);
                    }
                });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                int cnt = 0;
                for (; ; ) {
                    Thread.sleep(1000);
                    emitter.onNext(cnt++);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " " + integer);
                    }
                });
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                int cnt = 0;
                for (; ; ) {
                    Thread.sleep(1000);
                    emitter.onNext(cnt++);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " " + integer);
                    }
                });
        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println(Thread.currentThread().getName());
                int cnt = 0;
                for (; ; ) {
                    Thread.sleep(1000);
                    emitter.onNext(cnt++);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(Thread.currentThread().getName() + " " + integer);
                    }
                });

        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        subscribe.dispose();
                    }
                });

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
