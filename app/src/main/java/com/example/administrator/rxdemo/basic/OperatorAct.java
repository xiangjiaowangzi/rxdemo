package com.example.administrator.rxdemo.basic;

import android.app.Activity;
import android.content.AbstractThreadedSyncAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import com.example.administrator.rxdemo.R;
import com.example.administrator.rxdemo.Utils;


import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.observers.SafeSubscriber;
import rx.schedulers.Schedulers;


/**
 * Created by Lbin on 2017/4/26.
 */

public class OperatorAct extends Activity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opera);

        //
        findViewById(R.id.empty).setOnClickListener(this);
        findViewById(R.id.never).setOnClickListener(this);
        findViewById(R.id.defer).setOnClickListener(this);


        //
        findViewById(R.id.range).setOnClickListener(this);
        findViewById(R.id.count).setOnClickListener(this);
        findViewById(R.id.range1).setOnClickListener(this);
        findViewById(R.id.buffer).setOnClickListener(this);
        findViewById(R.id.groub).setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.window).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.empty) {
            testEmpty();
        }
        if (v.getId() == R.id.never) {
            testNever();
        }
        if (v.getId() == R.id.defer) {
            testDefer();
        }

        //

        if (v.getId() == R.id.range) {
            testRange();
        }
        if (v.getId() == R.id.count) {
            testCount();
        }
        if (v.getId() == R.id.range1) {
            testRange1();
        }
        if (v.getId() == R.id.buffer) {
            testBuffer();
        }
        if (v.getId() == R.id.groub) {
            testGroubBy();
        }
        if (v.getId() == R.id.scan) {
            testScan();
        }
        if (v.getId() == R.id.window) {
            testWindow();
        }
    }

    void testRange() {
        Observable.range(5, 5).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Utils.print("range 类似for循环 " + integer + "");
            }
        });
    }

    void testCount() {
        Observable observable1 = Observable.range(0, 2);
        Observable.from(s).count().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Utils.print(" count 执行一次 总数 " + integer + "");
            }
        });
    }

    List<String> s = Utils.getStringList();

    void testRange1() {
        Observable<Integer> obs = Observable.range(0, 3);
        obs.flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                Utils.print("  integer " + integer);
                return Observable.from(s).count();
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Utils.print("  最后 count  integer " + integer);
            }
        });
    }

    void testStart() {

    }

    void testEmpty() {
        Observable.empty().subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Utils.print("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Utils.print("onError");
            }

            @Override
            public void onNext(Object o) {
                Utils.print("onNext");
            }
        });
    }

    void testNever() {
        Observable.never().subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Utils.print("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Utils.print("onError");
            }

            @Override
            public void onNext(Object o) {
                Utils.print("onNext");
            }
        });
    }

    void testDefer() {
        Observable<String> obs = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Utils.print("call");
                Thread thread = new Thread();
                try {
                    thread.sleep(2000);
                    subscriber.onNext("5");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
        obs.subscribe(new SafeSubscriber<String>(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        }));
        obs.subscribe(s1 -> {

        });
//        Obs
//        obs.create(new Observable.Operator<String , String>(){
//
//                       @Override
//                       public Subscriber<? super String> call(Subscriber<? super String> subscriber) {
//                           return null;
//                       }
//                   };
        Observable.just("111" , "2222").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s + " 呵呵 ";
            }
        }).flatMap(new Func1<String, Observable<String>>() {

            @Override
            public Observable<String> call(String o) {
                return Observable.just(o + "字啊一次变换");
            }
        }).filter(new Func1<Object, Boolean>() {

            @Override
            public Boolean call(Object o) {
                return true;
            }
        }).take(1).first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {

            @Override
            public void call(Object o) {

            }
        });
        Observable<String> defIObs = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("5");
                        Thread thread = new Thread();
                        try {
                            thread.sleep(2000);
                            Utils.print("defer 如果有线程阻塞，onSubscribe 会阻塞一段时间，但是用了defer不会 ，除非是放在线程阻塞里面");
                            subscriber.onCompleted();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });
        defIObs
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onCompleted() {
                        Utils.print("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Utils.print("onError");
                    }

                    @Override
                    public void onNext(String o) {
                        Utils.print("onNext " + o);
                    }
                });
    }

    void testBuffer() {
        Observable.from(s).buffer(2).subscribe(list -> {
            Utils.print(" 长度 " + list.size());
            for (String a : list) Utils.print(" 缓存的个数有 " + a);
        });

        Observable.from(s).buffer(5, 3).subscribe(list -> {
            Utils.print("buffer操作符是 第一个代表每次缓存的个数，如果不够就是剩下的 ，skip 代表每次去除的个数，可以理解缓存的次数就是 size / skip");
            Utils.print(" 长度 " + list.size());
            for (String a : list) Utils.print(" 每次去除1个，缓存的个数有 " + a);
        });

        Observable.create(subscriber -> {
            Thread thread = new Thread();
            int count = 0;
            try {
                while (count < 20) {
                    thread.sleep(2000); // 睡2秒
                    subscriber.onNext(count);
                    count++;
                }
                subscriber.onCompleted();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).buffer(4, 8, TimeUnit.SECONDS).subscribe(objects -> {
            Utils.print("缓存时间与count 一样 区别是 一个是个数 为单位 一个是时间为单位");
            Utils.print(" 长度 " + objects);
        });
    }

    void testGroubBy() {
        Observable.range(1, 5).groupBy(s1 -> {
            Utils.print(" grouby 给每个数据分组带有key值 但是顺序还是一致 ");
            return s1 % 3;
        }).subscribe(booleanIntegerGroupedObservable -> {
            booleanIntegerGroupedObservable.subscribe(integer -> {
                Utils.print("key " + booleanIntegerGroupedObservable.getKey());
                Utils.print("value " + integer);
            });
        });
    }

    void testScan() {
        Observable.range(1, 5).scan((integer, integer2) -> {
            Utils.print(" scan 操作符 让输出根据指定函数的输出 这里是累加");
            return integer + integer2;
        }).subscribe(integer -> {
            Utils.print(" 输出结果讲道理 1 ，3 , 6 ， 10 。。。 " + integer);
        });
    }

    void testWindow() {
        Observable.from(s).window(5, 2).subscribe(stringObservable -> {
            Utils.print(" window 类似 buffer 不过一个是输出数据 一个是输出Observablec ");
            Utils.print(" window 建议不要使用 skip 参数 会将相同的放在一块了");
            Utils.print(" 就像 map 和 flap 一样 ");
            stringObservable.subscribe(s1 -> {
                Utils.print(" 我可以去掉第一个 " + s1);
            });
        });
    }

}
