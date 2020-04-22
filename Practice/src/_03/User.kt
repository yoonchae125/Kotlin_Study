package _03

class User (val id: Int, val name:String, val address:String)
fun saveUser(user:User){
	if(user.name.isEmpty()){
		throw IllegalArgumentException(
			"Can't save user ${user.id}: empty name")
	}
	if(user.address.isEmpty()){
		throw IllegalArgumentException(
			"Can't save user ${user.id}: empty Address")
	}	
}
fun saveUser2(user:User){
	fun validate(value:String, fieldName:String){
		if (value.isEmpty()){
			throw IllegalArgumentException(
			"Can't save user ${user.id}: empty $fieldName")
		}
	}
	validate(user.name, "Name")
	validate(user.address, "Address")
}
fun main(args:Array<String>){
//	saveUser(User(1,"",""))
	saveUser2(User(1,"",""))
}