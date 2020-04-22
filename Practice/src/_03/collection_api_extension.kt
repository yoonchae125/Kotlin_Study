package _03


fun parsePath(path:String){
	val directory = path.substringBeforeLast("/")
  val fullName = path.substringAfterLast("/")
  val fileName = fullName.substringBeforeLast(".")
  val extension = fullName.substringAfterLast(".")
  println("Dir: $directory, name: $fileName, ext: $extension")
}
fun parsePath2(path: String){
	val regex = """(.+)/(.+)\.(.+)""".toRegex()
	val mathResult = regex.matchEntire(path)
	if(mathResult!=null){
		val(directory, filename,extension) = mathResult.destructured
		println("Dir: $directory, name: $filename, ext: $extension")
	}
}
fun main(args:Array<String>){
	val strings:List<String> = listOf("first", "second"," fourteenth")
	println(strings.last())
	var list = listOf("args: ",*args)
	println(list)
	parsePath("/User/yole/kotlin-book/chapter.adoc")
	parsePath2("/User/yole/kotlin-book/chapter.adoc")
}