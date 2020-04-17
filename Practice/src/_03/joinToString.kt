package _03

fun main(args:Array<String>){
	val a = listOf(1,3,5)
	var str = joinToString(a,separator=",",prefix="(",postfix=")")
	println(str)
}

fun <T> joinToString(
	collection : Collection<T>,
	separator: String,
	prefix: String,
	postfix: String
):String{
	val result = StringBuilder(prefix)
	for((index,element) in collection.withIndex()){
		if(index>0) result.append(separator) //첫 원소에 구분자 붙이지 않기 위해 
		result.append(element)
	}
	result.append(postfix)
	return result.toString()
}