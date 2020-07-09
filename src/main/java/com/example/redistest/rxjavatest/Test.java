package com.example.redistest.rxjavatest;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Test {

    public static void main(String[] args) throws Exception {
        Observable.create(new ObservableOnSubscribe<Integer>() { // 第一步：初始化Observable
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println("Observable emit 1");
                e.onNext(1);
                System.out.println("Observable emit 2");
                e.onNext(2);
                System.out.println("Observable emit 3");
                e.onNext(3);
                e.onComplete();
                System.out.println("Observable emit 4");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() { // 第三步：订阅

            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });

    }

}





























