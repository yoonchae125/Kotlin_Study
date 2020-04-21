package _03



fun main(args:Array<String>){
	var str="hello";
	println(str.lastChar())
	println(listOf(1,2,3).joinToString(","))
	println(listOf("a","b","c").join(","))
}
fun String.lastChar() : Char = this.get(this.length-1)

fun <T> Collection<T>.joinToString(
	separator: String = ",",
  prefix: String = "",
  postfix: String = ""
):String{
  val result = StringBuffer(prefix)
  for((index, element) in this.withIndex()){
    if (index>0) result.append(separator)
    result.append(element)
  }
  result.append(postfix)
  return result.toString()
  
}
fun Collection<String>.join(separator: String = ",",
  prefix: String = "",
  postfix: String = ""):String = this.joinToString(separator, prefix, postfix);