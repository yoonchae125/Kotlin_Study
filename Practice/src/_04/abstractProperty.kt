package _04
interface User1 {
	val nickname: String
}
class Subscribeuser(val email:String): User1{
  override val nickname: String
  	get() = email.substringBefore('@')
}
fun main(args:Array<String>){
	
}