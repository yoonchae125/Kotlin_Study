package _02
enum class Color (val r:Int, val g:Int, val b:Int){
	// 상수 목록
	RED(255,0,0), ORANGE(255,165,0), YELLOW(255,255,0),
	GREEN(0,255,0), BLUE(0,0,255), INDIGO(75,0,130), VIOLET(238,130,238);
	// 함수 정의
	fun rgb() = (r*256+g)*256+b
}
fun getWarmth(color:Color) = when(color){
  Color.RED, Color.ORANGE, Color.YELLOW -> "warm"
  Color.GREEN -> "neutral"
  Color.BLUE, Color.INDIGO, Color.VIOLET -> "cold"
  else -> throw Exception("nothing")
}
fun main(args:Array<String>){
	print(getWarmth(Color.RED))
	print(Color.RED.rgb())
}