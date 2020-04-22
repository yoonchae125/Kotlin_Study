package _03

class User2 (val id: Int, val name:String, val address:String) 
fun User2.validateBeforeSave(){
	fun validate(value: String, fieldName:String){
		if(value.isEmpty()){
			throw IllegalArgumentException("Can't save user $id: empty $fieldName")
		}
	}
	validate(name,"Name")
	validate(address,"Address")
}
fun saveUser(user:User2){
	user.validateBeforeSave()
}
fun main(args:Array<String>){
	saveUser(User2(1,"",""))
}