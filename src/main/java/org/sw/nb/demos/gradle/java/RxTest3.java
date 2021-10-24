package org.sw.nb.demos.gradle.java;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.concurrent.CancellationException;

public class RxTest3 {

    static final Function<Object, Completable> CANCEL_COMPLETABLE = new Function<Object, Completable>() {
        @Override
        public Completable apply(Object ignore) throws Exception {
            System.out.println("Completable -> onError");
            return Completable.error(new CancellationException());
        }
    };

    // subject，模拟声明周期
    public static PublishSubject observable = PublishSubject.create();

    public static void main(String[] args) {

        // 待测试Completable
        Completable.create(emitter -> {
            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println("onError " + emitter.isDisposed() + " - " + Thread.currentThread().getName());
                    System.out.println("onError");
                    observable.onNext(new Object());
                }
            };
            Runnable runnable1 = new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("onComplete " + Thread.currentThread().getName());
                    emitter.onComplete();
                }
            };
            Thread errorThread = new Thread(runnable, "thread-err");
            Thread completeThread = new Thread(runnable1, "thread-complete");
            errorThread.start();
            completeThread.start();
        })
                // 模拟RxLifecycle，连接当前Completable和LifeCycle Observable，当进入设置的生命周期，触发CANCEL_COMPLETABLE
                // 发射一个异常，试图终止Completabe流。
                .compose(new CompletableTransformer() {
                    @Override
                    public CompletableSource apply(Completable upstream) {
                        return Completable.ambArray(upstream, observable.flatMapCompletable(CANCEL_COMPLETABLE));
                    }
                })
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("onSubscribe called " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete called " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError called " + Thread.currentThread().getName());
                    }
                });
    }
}
