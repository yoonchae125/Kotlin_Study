package _04

interface Expr
class Num(val value:Int):Expr
class Sum(val left: Expr, val right:Expr):Expr
fun eval(e:Expr):Int =
	when(e){
		is Num -> e.value
		is Sum -> eval(e.left)+eval(e.right)
		else -> // else 분기 강제화
			throw IllegalArgumentException("Unknown expression")
	}
sealed class Expr2{
	class Num(val value:Int):Expr2()
	class Sum(val left:Expr2, val right:Expr2):Expr2()
}
fun eval2(e:Expr2):Int =
	when(e){
		is Expr2.Num -> e.value
		is Expr2.Sum -> eval2(e.left)+ eval2(e.right)
	}