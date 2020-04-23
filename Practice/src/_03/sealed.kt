package _03

sealed class Expr{
	class Num(val vlaue:Int):Expr()
	class Sum(val left:Expr, val right:Expr):Expr()
}
fun eval(e:Expr): Int=
	when(e){
		is Expr.Num -> e.value
		
	}