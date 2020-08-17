package com.chaeyoon.rxjavastudy.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class HotObservable {
    // 지속적으로 상태 변화 구독


    fun connectable() {
        // ConnectableObservable
        val observable = Observable.interval(1, TimeUnit.SECONDS)
            .publish()

        observable.subscribe() // 1, 2, 3, 4 ...

        // connect 하면 발행 시작
        observable.connect()

        Thread.sleep(3000)

        observable.subscribe() // 4, 5, ...
    }

    fun refCount() {
        // 자기 자신을 구독하는 observer 갯수 알고 있음
        val observable = Observable.interval(1, TimeUnit.SECONDS)
            .publish().refCount()

        val disposable1 = observable.subscribe {

        } // 1, 2, 3, 4 ...

        Thread.sleep(3000)

        val disposable2 = observable.subscribe {

        } // 4, 5, ...

        disposable1.dispose()
        disposable2.dispose()
        // 구독하는 Observer가 0이 되면 발행 멈춤

        observable.subscribe() // 1, 2, 3, 4 ...
    }

    fun publishSubject() {
        // subject 내부에 list<Observer>가 있다.
        // subscribe() 를 하면 list.add(observer)
        // onNext() 를 하면 list.forEach{ observer.onNext() }
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.subscribe() // observable -> 1,2,3,4
        publishSubject.onNext(1) //observer
        publishSubject.onNext(2) //observer
        publishSubject.onNext(3) //observer
        publishSubject.onNext(4) //observer
        publishSubject.subscribe() // observable -> X
    }

    fun behaviorSubject() {
        val behaviorSubject = BehaviorSubject.create<Int>()
        behaviorSubject.subscribe() // observable -> 1,2,3,4
        behaviorSubject.onNext(1) //observer
        behaviorSubject.onNext(2) //observer
        behaviorSubject.onNext(3) //observer
        behaviorSubject.onNext(4) //observer
        behaviorSubject.subscribe() // observable -> 4 : 이전 것부터 받음
    }
}