package _05

import java.awt.Button

data class Person(val name:String, val age:Int)
fun findTheOldest(people:List<Person>){
  var maxAge = 0
  var theOldest: Person? = null
  for(person in people){
    if(person.age > maxAge){
      maxAge = person.age
      theOldest = person
    }
  }
  println(theOldest)
}
fun main(args:Array<String>){
	val people = listOf(Person("채윤",24),Person("윤지",26))
	people.maxBy(Person::age)
//	val names = people.joinToString(separator=" ",transform={p:Person->p.name})
	val names = people.joinToString(" ") {p:Person -> p.name}
	println(names)
//	findTheOldest(people)
	val sum = {x:Int, y:Int ->
		println("add")
		x+y
	}
	println(sum(1,2))
	
	val errors = listOf("403 Forbidden", "404 Not Found")
	printMessageWithPrefix(errors, "Errors:")
	
	val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
	printProblemCounts(responses)
	run(::salute)

//	val action = {person:Person,message:String -> sendEmail(person,message)}
//	val nextAction = ::sendEmail
}
fun salute() = println("Salute!")
fun printMessageWithPrefix(messages:Collection<String>, prefix: String){
	messages.forEach{
		println("$prefix $it")
	}
}
fun printProblemCounts(responses: Collection<String>){
	var clientErrors = 0
	var serverErrors = 0
	responses.forEach{
		if(it.startsWith("4")){
			clientErrors++
		}else if(it.startsWith("5")){
			serverErrors++
		}
	}
	println("$clientErrors client errors, $serverErrors server errors")
}
fun tryToCountButtonClicks(button:Button):Int{
	var clicks = 0
//	button.onClick{ clicks++ }
	return clicks
}