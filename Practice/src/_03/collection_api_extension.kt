package _03



fun main(args:Array<String>){
	val strings:List<String> = listOf("first", "second"," fourteenth")
	println(strings.last())
	var list = listOf("args: ",*args)
	println(list)
}