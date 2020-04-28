package _04

class User constructor(_nickname: String){ // 파라미터가 하나만 있는 주 생성자
	val nickname: String
	init{ // 초기화 블록
		nickname = _nickname
	}
}
fun main(args:Array<String>){
	println("12.345-6.A".split("\\.|-".toRegex()))

	println("12.345-6.A".split(".", "-"))
}