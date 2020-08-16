package com.chaeyoon.rxjavastudy.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import java.util.concurrent.TimeUnit

fun kotlinOperator() {
    // let, with, run, apply, also
    listOf<Boolean>().let {
        it.forEach {

        }
        1 // return
    }
    with(listOf<Boolean>()) {
        forEach {

        }
        1
    }
    listOf<Boolean>().run {
        forEach {

        }
        1
    }
    listOf<Boolean>().apply {
        forEach {

        }
    }
}

fun collectionOperators() {
    listOf(1, 2, 3).reduce { acc, number ->
        acc + number
    }
    listOf(1, 2, 3).fold(10f) { acc, number ->
        acc + number
    }
    listOf(listOf(1, 2, 3), listOf(3, 4, 5)).flatten() // -> (1,2,3,3,4,5)

    listOf(1, 2, 3).all { it > 2 } // false
    listOf(1, 2, 4).any { it > 2 } // true

    listOf(1, 1, 2, 3).distinct() // 1, 2, 3
    listOf(1, 1, 2, 3).groupBy {
        it == 1
    }
    // -> map(true, 1, 1)
    // -> map(false, 2, 3)
}

fun rxJavaOperators() {
    // Collection Operator + ㄱㅣ타 등 등
    Single.just(true).map {

    }

    // 검색할 때 사용 -> ㄱ ㅁ ㅓ 하나 씩 다 검색하는 것이 아니라
    // 어느 정도 완성됐을 때까지 기다린다
    Observable.just(true)
        .debounce(1, TimeUnit.SECONDS)

    Single.concat(
        Single.just(listOf(1, 1, 1)),
        Single.just(listOf(2, 2))
    ).subscribe {
        // 1, 1, 1, 2, 2
    }

    Single.merge(
        Single.just(listOf(1, 1, 1)),
        Single.just(listOf(2, 2))
    ).subscribe {
       // 1, 2, 2, 1, 1
    }
    Single.zip<Boolean, Int, String>(
        Single.just(true),
        Single.just(1),
        BiFunction{ first, second ->
            ""
        }
    ).subscribe({

    },{

    })
}