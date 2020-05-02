package _02

fun isLetter(c:Char)=c in 'a'..'z' ||c in 'A'..'Z'
fun isNotDigit(c:Char)=c !in '0'..'9'
fun main(args:Array<String>){
	println(isLetter('>'))
	println(isNotDigit('d'))
	
	
}
val number = 200
//val percentage=
//	if (number in 0..100)
//		number
//	else
//		throw IllegalArgumentException("value must be between 0 and 100: $number")