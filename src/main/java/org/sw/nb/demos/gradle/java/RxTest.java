package org.sw.nb.demos.gradle.java;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

public class RxTest {
    public static void main(String[] args) {
        Completable.create(emitter -> {
            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println("onError " + emitter.isDisposed() + " - " + Thread.currentThread().getName());
                    System.out.println("onError");
                    // 自定义的Reactive Source，自己做dispose判断
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IllegalAccessError("zwx"));
                    }
                }
            };
            Runnable runnable1 = new Runnable() {
                public void run() {
                    System.out.println("onComplete");
                    emitter.onComplete();
                }
            };
            Thread thread = new Thread(runnable);
            Thread thread1 = new Thread(runnable1);
            thread1.start();
            thread.start();
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe called");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete called");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError called");
            }
        });
    }
}
