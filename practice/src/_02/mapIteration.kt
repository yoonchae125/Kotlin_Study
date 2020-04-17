package _02

import java.util.TreeMap


fun main(args:Array<String>){
	val binaryReps = TreeMap<Char, String>() //key에 대해 정렬하기 위해 사용

	for(c in 'A'..'F'){ // A부터 F까지 문자의 범위를 사용해 이터레이션
		val binary = Integer.toBinaryString(c.toInt()) // ASCII코드를 2진 표현으로 바꿈
		binaryReps[c] = binary
	}
	for((letter,binary) in binaryReps){
		println("$letter = $binary")
	}
}