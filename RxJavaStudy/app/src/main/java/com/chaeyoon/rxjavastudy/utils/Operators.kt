package com.chaeyoon.rxjavastudy.utils

import java.util.*

fun collectionOperators() {
    listOf(1, 2, 3).reduce { acc, number ->
        acc + number
    }
    listOf(1, 2, 3).fold(10f) { acc, number ->
        acc + number
    }
    listOf(1, 1, 2, 3).distinct() // 1, 2, 3
    listOf(1, 1, 2, 3).groupBy {
        it == 1
    }
    // -> map(true, 1, 1)
    // -> map(false, 2, 3)
}
fun rxJavaOperators() {

}