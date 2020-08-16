import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observables.ConnectableObservable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import java.util.concurrent.TimeUnit

class Sample {
    fun example() {
        // Consumer
        Single.just(true)
            .subscribe({ result ->
                // onSuccess()
            }, { error ->
                // onError()
            })
        Single.just(true)
            .subscribe(object : DisposableSingleObserver<Boolean>(){
                override fun onSuccess(t: Boolean?) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable?) {
                    TODO("Not yet implemented")
                }

            })
    }
    fun subscribe(){
        Single.just(true)
            .subscribe{ result ->
                println(result)
            }

        // SingleCreate 만들고
        // SingleCreate subscribe 하면
        // SingleCreate 에 넘겨준 source 실행(subscribe())
        // emitter onSuccess() -> downStream.onSuccess()
        Single.create<Int>{emitter ->
            println("source 가 subscribe 했다 ")
            emitter.onSuccess(1)
        }
            .map { "map String" }
            .subscribe { result ->

        }
    }
}