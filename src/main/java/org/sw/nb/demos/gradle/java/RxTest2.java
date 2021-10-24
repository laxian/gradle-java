package org.sw.nb.demos.gradle.java;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import java.util.concurrent.CancellationException;

public class RxTest2 {

    public static void main(String[] args) {
        Observable.create(emitter -> {
            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println("onError " + emitter.isDisposed() + " - " + Thread.currentThread().getName());
                    System.out.println("onError " + Thread.currentThread().getName());
                    emitter.onError(new IllegalAccessError("zwx"));
                }
            };
            Runnable runnable1 = new Runnable() {
                public void run() {
                    System.out.println("onComplete " + Thread.currentThread().getName());
                    emitter.onComplete();
                }
            };
            Thread thread = new Thread(runnable);
            Thread thread1 = new Thread(runnable1);
            thread1.start();
            thread.start();
        })
                .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe called " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Object o) {
                System.out.println("onNext called " + Thread.currentThread().getName());
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
