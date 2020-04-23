package _04

interface Clickable {
	fun click()
	fun showOff() = println("I'm clickable!")
}
//class Button: Clickable, Focusable{
//	override fun click() = println("I was clicked")
//	override fun showOff(){
//		super<Clickable>.showOff()
//		super<Focusable>.showOff()
//	}
//}
interface Focusable{
	fun setFocus(b:Boolean){
		println("I ${if(b) "got" else "lost"} focus")
	}
	fun showOff() = println("I'm focusable!")
}
fun main(args:Array<String>){
//	Button().showOff()
}
open class RichButton: Clickable{ // open
	fun diable() {} // final
	open fun animate() {} // open
	override fun click() {} // open, 오버라이드한 메소드는 기본적으로 열려있다
}