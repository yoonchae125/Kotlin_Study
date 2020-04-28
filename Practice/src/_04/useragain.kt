package _04

class User2(val name: String){
  var address: String = "unspecified"
  	set(value:String){
      println("""
      	Address was changed for $name: 
      	"$field" -> "$value".""".trimIndent())  // 뒷받침하는 필드 값 읽기
      field = value // 뒷받침하는 필드 값 변경하기
    }
}
fun main(args:Array<String>){
	val chaeyoon = User2("yoon")
	chaeyoon.address = "서울"
	chaeyoon.address = "경기"
	println(chaeyoon.address)
}