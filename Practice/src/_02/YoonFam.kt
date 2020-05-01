package _02

import java.util.TreeMap

enum class YoonFam (val nam:String, val age:Int){
	Chaeyoon("채윤",24), YoonJi("윤지",26), KyungHa("경하",52), NamYi("남이",53);
	fun nameAndage () = println("$name $age")
}
fun ageWhere(fam:YoonFam) = when(fam){
	YoonFam.Chaeyoon -> "채발"
	YoonFam.YoonJi -> "윤발"
	else -> "엄빠"
}
abstract class Animated {
	abstract fun animate() // open, 추상 함수, 하위 클래스에서 반드시 오버라이드해야 한다.
	open fun stopAnimating() {} // open
	fun animateTwice() {} // final
	// 비추상 함수는 기본적으로 final이지만 open 변환자로 override 허용 가능
}
data class Person(val name: String){
  object NameComparator:Comparator<Person>{
    override fun compare(p1:Person, p2:Person):Int = 
    	p1.name.compareTo(p2.name)
  }
}
object Payroll{
  val allEmployees = arrayListOf<Person>(Person("채윤"))
  fun calculateSalary(){
    for(person in allEmployees){
      println(person)
    }
  }
}
//window.addListentre(object:Listener(){
//	
//})
fun main(args:Array<String>){
	Payroll.calculateSalary()
//	println(YoonFam.Chaeyoon.nameAndage())
//	println(ageWhere(YoonFam.NamYi))
	val binaryReps = TreeMap<Char, String>() //key에 대해 정렬하기 위해 사용
	for(c in 'A'..'F'){ // A부터 F까지 문자의 범위를 사용해 이터레이션
		val binary = Integer.toBinaryString(c.toInt()) // ASCII코드를 2진 표현으로 바꿈
		binaryReps[c] = binary
	}
	for((letter,binary) in binaryReps){
		println("$letter = $binary")
	}
	
	println("12.345-6.A".split(".", "-"))
}