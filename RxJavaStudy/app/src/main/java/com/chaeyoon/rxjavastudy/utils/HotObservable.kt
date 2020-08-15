package com.chaeyoon.rxjavastudy.utils

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.security.auth.Subject

class HotObservable{
    // 지속적으로 상태 변화 구독
    fun publishSubject(){
        val publishSubject = PublishSubject.create<Int>()
        publishSubject.subscribe() // observable -> 1,2,3,4
        publishSubject.onNext(1) //observer
        publishSubject.onNext(2) //observer
        publishSubject.onNext(3) //observer
        publishSubject.onNext(4) //observer
        publishSubject.subscribe() // observable -> X
    }
    fun behaviorSubject(){
        val behaviorSubject = BehaviorSubject.create<Int>()
        behaviorSubject.subscribe() // observable -> 1,2,3,4
        behaviorSubject.onNext(1) //observer
        behaviorSubject.onNext(2) //observer
        behaviorSubject.onNext(3) //observer
        behaviorSubject.onNext(4) //observer
        behaviorSubject.subscribe() // observable -> 4 : 이전 것부터 받음
    }
}