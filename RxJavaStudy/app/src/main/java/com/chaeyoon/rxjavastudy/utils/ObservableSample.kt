import java.util.*

class ObservableSample: Observable(){
    fun run(){
        val list = listOf(1,2,3,4) // 발행할 이벤트

        // list에서 원소 하나 씩을 pull 한다.
        list.forEach{ _ ->
            // 가져온 원소 하나를 push 한다.
            setChanged()
            notifyObservers()
        }
    }
    // 명령형
    fun sumImperative(arr:List<Int>):Int{
        var result = 0
        arr.forEach {
            result += it
        }
        return result
    }
    // 함수형
    fun sumFunctional(arr:List<Int>):Int =
        arr.reduce { acc, i -> acc + arr[i] }

}