package _04

abstract class Animated {
	abstract fun animate() // open, 추상 함수, 하위 클래스에서 반드시 오버라이드해야 한다.
	open fun stopAnimating() {} // open
	fun animateTwice() {} // final
	// 비추상 함수는 기본적으로 final이지만 open 변환자로 override 허용 가능
}