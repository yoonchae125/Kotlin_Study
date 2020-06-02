package _05

import java.lang.StringBuilder

data class Person(val name: String, val age: Int)

fun main(args: Array<String>) {

    val numbers = mapOf(1 to "one", 2 to "two")
    println(numbers.mapValues { it.value.toUpperCase() })
    println(numbers.maxBy { it.key }!!.key)

    // 어떤 사람의 나이가 27살 이하인지 판단하는 함수
    val canBeInClub27 = { p: Person -> p.age <= 27 }
    println(canBeInClub27(Person("채윤", 24)))
//    val people = mutableListOf(Person("Alice", 26), Person("Bob", 31))
    val people = mutableListOf(Person("Alice", 26), Person("Bob", 31), Person("Diva", 26))

    println(people.all(canBeInClub27))
    println(people.any(canBeInClub27))
    println(people.count(canBeInClub27))
    println(people.find(canBeInClub27))
    println(people.find { it.age > 27 })

    // groupBy
//    people.add(
    println(people.groupBy(Person::age))
    val list = listOf("a", "ab", "b")
    println(list.groupBy(String::first))

    // flatMap
    data class Book(val title: String, val authors: List<String>)

    val books = listOf(Book("코틀린", listOf("개발자", "천재")), Book("해커스", listOf("영재")))
    println(books.flatMap { it.authors }.toSet()) // books 컬렉션에 있는 책을 쓴 모든 저자의 집합
    // 문자열에 적용
    val strings = listOf("abc", "def")
    println(strings.flatMap { it.toList() })
    println(strings.map { it.toList() })
    var filteredPeople = people.map(Person::name).filter { it.startsWith("A") }
    println(filteredPeople)

    filteredPeople = people.asSequence()
        .map(Person::name)
        .filter { it.startsWith("A") }
        .toList()
    println(filteredPeople)

    listOf(1, 2, 3, 4).asSequence()
        .map { print("map($it) "); it * it }
        .filter { print("filter($it) "); it % 2 == 0 }
        .toList()
    println(listOf(1, 2, 3, 4).asSequence()
        .map { it * it }.find { it > 3 })

    val naturalNumbers = generateSequence(0) { it + 1 }
    val numbersTo100 = naturalNumbers.takeWhile { it <= 100 }
    println(numbersTo100.sum())

    println(alphabet())
}

//fun alphabet(): String {
//    val result = StringBuilder()
//    for (letter in 'A'..'Z') {
//        result.append(letter)
//    }
//    result.append("\nNow I know the alphabet!")
//    return result.toString()
//}
//fun alphabet(): String {
//    val result = StringBuilder()
//    return with(result) {
//        for (letter in 'A'..'Z') {
//            append(letter)
//        }
//        append("\nNow I know the alphabet!")
//        this.toString()
//    }
//}
//fun alphabet() = StringBuilder().apply {
//    for (letter in 'A'..'Z') {
//        append(letter)
//    }
//    append("\nNow I know the alphabet!")
//}.toString()
fun alphabet() = buildString {
    for (letter in 'A'..'Z'){
        append(letter)
    }
    append("\nNow I know the alphabet!")
}