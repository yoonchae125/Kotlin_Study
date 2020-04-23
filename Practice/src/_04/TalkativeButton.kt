package _04

internal open class TalkativeButton: Focusable{
	private fun yell() = println("Hey!")
	protected fun whisper() = println("Let's talk!")
}
//fun TalkativeButton.giveSpeech() { // public 멤버가 자신의 internal 수신 타입인 TalkiveButton을 노출함
//	yell() // private 멤버 yell에 접근 불가
//	whisper() // protected 멤버 whisper에 접근 불가 
//}
fun main(args:Array<String>){
//	TalkativeButton().whisper()
}