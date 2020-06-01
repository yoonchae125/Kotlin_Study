# 코루틴과 Asynce / Await

## 1. 코루틴이란?

- 코루틴은 컴퓨터 프로그램 구성 요수 중 하나로 비선점형 멀티태스킹을 수행하는 일반화한 서브루틴이다.
- 코루틴은 실행을 일시 중단(suspend)하고 재개(resume)할 수 있는 여러 진입 지점을 허용한다.
- **서브루틴**은 여러 명령어를 모아 이름을 부여해서 반복 호출할 수 있게 정의한 프로그램 구성 요소로, 다른 말로 함수라고 부르기도 한다.

- 서브루틴에 진입(해당 함수 호출)하면, 그때마다 활성 레코드라는 것이 스택에 할당되면서 서브루틴 내부의 로컬 변수 등이 초기화된다. 서브루틴에서 return하고 나면 활성 레코드가 스택에서 사라지기 때문에 실행 중이던 모든 상태를 잃어버린다.
- **멀티태스킹**은 여러 작업을 동시에 수행하는 것처럼 보이거나 실제로 동시에 수행하는 것이다.

- **비선점형**이란 멀티태스킹의 각 작업을 수행하는 참여자들의 실행을 운영체제가 강제로 일시 중단시키고 다른 참여자를 실행하게 만들 수 없다는 뜻이다. 따라서 각 참여자들이 서로 자발적으로 협력해야만 비선점형 멀티태스킹이 제대로 작동할 수 있다.
- 따라서 **코루틴**이란 서로 협력해서 실행을 주고받으면서 작동하는 여러 서브루틴을 말한다.



## 2. 코틀린의 코루틴 지원: 일반적인 코루틴

- 코틀린의 특정 코루틴을 언어가 지원하는 형태가 아니라, 코루틴을 구현할 수 있는 기본 도구를 언어가 제공하는 형태다.

- 코틀린의 코루틴 지원 기본 기능들은 kotlin.coroutine 패키지 밑에 있고, 코틀린 1.3부터는 코틀린을 설치하면 별도의 설정 없이도 모든 기능을 사용할 수 있다.

### (1) 여러 가지 코루틴

> 코루틴을 만들어주는 코루틴 빌더. 코틀린에서 코루틴 빌더에 우너하는 동작을 람다로 넘겨서 코루틴을 만들어 실행하는 방식으로 코루틴을 활용한다.

#### Kotlinx.coroutines.CoroutineScope.launch

- launch는 코루틴을 잡으로 반환하며, 만들어진 코루틴은 기본적으로 즉시 실행된다.
- launch가 반환한 Job의 cancel()을 호출해 코루틴 실행을 중단시킬 수 있다.
- launch가 작동하려면 CoroutineScope 객체가 블록의 this로 지정돼야하는데, 다른 suspend 함수 내부라면 해당 함수가 사용 중인 CoroutineScope가 있겠지만, 그렇지 않은 경우에는 GlobalScope를 사용하면 된다.

```kotlin
fun launchInGlobalScope(){
  GlobalScope.launch{
    log("coroutine started.")
  }
}
fun main(){
  log("main() started")
  launchInGlobalScope()
  log("launchInGlobalScope() executed")
  Thread.sleep(5000L)
  log("main() terminated")
}
```

- 메인 함수와 GlobalScope.launch가 만들어낸 코루틴은 서로 다른 스레드에서 실행된다.
- GlobalScope는 메인 스레드가 실행 중인 동안만 코루틴의 동작을 보장해준다.
- 위 코드에서 sleep()을 하지 않으면 main()이 바로 끝나고, 메인 스레드가 종료되면서 바로 프로그램 전체가 끝나버린다.
- 이를 방지하려면 비동기적으로 launch를 실행하거나, launch가 모두 다 실행될 때까지 기다려야 한다.
- 코루틴의 실행이 끝날 때까지 현재 스레드를 블록시키는 함수로 runBlocking()이 있다. runBlocking은 CoroutineScope의 확장 함수가 아닌 일반 함수이기 때문에 별도의 코루틴 스코프 객체 없이 사용 가능하다.

```kotlin
fun runBlockingExample(){
  ruBlocking{
    launch{
      log("coroutine started.")
    }
  }
}
```

- 위 코드는 스레드가 모두 main 스레드이다.

- 코루틴들은 서로 yield()해주면서 협력할 수 있다.

```kotlin
fun yieldExample(){
  runBlocking{
    launch{
      log("1")
      yield()
      log("3")
      yield()
      log("5")
    }
    log("after first launch")
    launch{
      log("2")
      delay(1000L)
      log("4")
      delay(1000L)
      log("6")
    }
    log("after second launch")
  }
}

>> after first launch, after second launch, 1 2 3 5 4 6
```

- launch는 즉시 반환 된다.
- runBlocking 내부 코루틴이 모두 끝난 다음에 반환된다.
- delay()를 사용한 코루틴은 그 시간이 지날 때까지 다른 코루틴에게 실행을 양보한다.
- delay 대신 yie면 1, 2, 3, 4, 5, 6이 표시될 것이다.

#### Kotlinx.coroutines.CoroutineScope.async

- async는 사실상 launch와 같은 일을 한다. 
- 유일한 차이는 launch가 Job을 반환하는 반변 async는 Deffered를 반환한다는 점뿐이다.

```kotlin
public fun CoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val newContext = newCoroutineContext(context)
    val coroutine = if (start.isLazy)
        LazyStandaloneCoroutine(newContext, block) else
        StandaloneCoroutine(newContext, active = true)
    coroutine.start(start, coroutine, block)
    return coroutine
}
```

```kotlin
public fun <T> CoroutineScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T> {
    val newContext = newCoroutineContext(context)
    val coroutine = if (start.isLazy)
        LazyDeferredCoroutine(newContext, block) else
        DeferredCoroutine<T>(newContext, active = true)
    coroutine.start(start, coroutine, block)
    return coroutine
}
```

- Deffered와 Job의 차이는, Job은 아무 타입 파라미터가 없는데 Deffered는 타입 파라미터가 있는 제네릭 타입이라는 점과 Deffered 안에는 await() 함수가 정의돼 있다는 점이다.
- Deffered의 타입 파라미터는 바로 Deffered 코루틴이 계산을 하고 돌려주는 값을 타입이다. Job은 Unit을 돌려주는 Deffered<Unit\>이라고 생각할 수도 있을 것이다.
- async는 코드 블록을 비동기로 실행할 수 있고(제공하는 코루틴 컨텍스트에 따라 여러 스레드를 사용하거나 한 스레드 안에서 제어만 왔다 갔다 할 수도 있다), async가 반환하는 Deffered의 await를 사용해서 코루틴이 결과 값을 내놓을 때까지 기다렸다가 결과 값을 얻어낼 수 있다.

```kotlin
fun sumAll{
  runBlocking{
    val d1 = async { delay(1000L): 1}
    log("after async(d1)")
    val d2 = async { delay(1000L): 2}
    log("after async(d2)")
    val d3 = async { delay(1000L): 3}
    log("after async(d3)")
    
    log("1+2+3 = ${d1.await() + d2.await() + d3.await()}")
    log("after await all & add")
  }
}
```

- d1, d2, d3를 순서대로 실행하면 총 6초 이상이 걸려야 하지만, 총 3초가 걸렸다. 
- 또한 async로 코드를 실행하는 데는 시간이 거의 걸리지 않음을 알 수 있다. 
- 그럼에도 불구하고 스레드를 여럿 사용하는 병렬 처리와 달리 모든 async 함수들이 메인 스레드 안에서 실행됨을 볼 수 있다.

- 실행하려는 작업이 시간이 얼마 걸리지 않거나 I/O에 의한 대기 시간이 크고, CPU 코어 수가 작아 동시에 실행할 수 있는 스레드 개수가 한정된 경우에는 특히 코루틴과 일반 스레드를 사용한 비동기 처리 사이에 차이가 커진다.

### (2) 코루틴 컨텍스트와 디스패처

- launch, async 등은 모두 CoroutineScope의 확장 함수다.
- 그런데 CoroutineScope에는 CoroutineContext 타입의 필드 하나만 들어있다.
- CoroutineScope는 CoroutineContext 필드를 launch 등의 확장 함수 내부에서 사용하기 위한 매개체 역할만을 담당한다.
- CoroutineContext는 실제로 코루틴이 실행 중인 여러 작업과 디스패치를 저장하는 일종의 맵이라 할 수 있다.
- 코틀린 런타임은 CoroutineContext를 사용해서 다음에 실행할 작업을 선정하고, 어떻게 스레드에 배정할지에 대한 방법을 결정한다.

```kotlin
launch{ // 부모 컨텍스트를 사용
	Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
}
launch(Dispatchers.Unconfined){ // 특정 스레드에 종속되지 않음
	Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
}
launch(Dispatchers.Default){ // 기본 디스패처를 사용
	Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
}
launch(newSingleThreadContext("MyOwnThread")){ // 새 스레드를 사용
	Log.d("codbs", "mainBlocking: I'm working in thread ${Thread.currentThread().name}")
}
```

- 전달하는 컨텍스트에 따라 서로 다른 스레드상에서 코루틴이 실행된다.

### (3) 코루틴 빌더와 일시 중단 함수

지금까지 살펴본 launch나 async, runBlocking은 모두 **코루틴 빌더**라고 불린다. 이들은 **코루틴을 만들어주는 함수**다.

**produce**

- 정해진 채널로 데이터를 스트림으로 보내는 코루틴을 만든다.
- ReceiveChannel<>을 반환하고 그 채널로부터 메시지를 전달받아 사용할 수 있다.

**actor**

- 정해진 채널로 메시지를 받아 처리하는 액터를 코루틴으로 만든다.
- SendChannel<>을 반환하고 채널의 send() 메소드를 통해 액터에게 메시지를 보낼 수 있다.

delay()와 yield()는 코루틴 안에서 특별한 의미를 지니는 함수들이다. 이런 함수를 **일시 중단 함수**라고 부른다.

**withContext**

- 다른 컨텍스트로 코루틴을 전환한다.

**withTimeout**

- 코루틴이 정해진 시간 안에 실행되지 않으면 예외를 발생시키게 한다.

**withTimeoutOrNull**

- 코루틴이 정해진 시간 안에 실행되지 않으면 null을 결과로 돌려준다.

**awaitAll**

- 모든 작업의 성공을 기다린다. 작업 중 어느 하나가 예외로 실패하면 awaitAll도 그 예외로 실패한다.

**joinAll**

- 모든 작업이 끝날 때까지 현재 작업을 일시 중단시킨다.



## 3. suspend 키워드와 코틀린의 일시 중단 함수 컴파일 방법

- 일시 중단 함수를 코루틴이나 일시 중단 함수가 아닌 함수에서 호출하는 것은 컴파일러 수준에서 금지된다.
- 함수 정의시 fun 앞에 **suspend**라는 키워드를 사용하면 일시 중단 함수를 만들 수 있다.

```kotlin
suspend fun yieldThreeTimes(){
  log("1")
  delay(1000L)
  yield()
  log("2")
  delay(1000L)
  yield()
  log("3")
  delay(1000L)
  yield()
  log("4")
}
fun suspendExample(){
  GlobalScope.launch{ yieldThreeTimes() }
}
```

일시 중단 함수 안에서 yield()를 해야 하는 경우 어떤 동작이 필요할지 생각해보자.

- 코루틴에 진입할 때와 코루틴에서 나갈 때 코루틴이 실행 중이던 상태를 저장하고 복구하는 등의 작업을 할 수 있어야 한다.
- 현재 실행 중이던 위치를 저장하고 다시 코루틴이 재개될 때 해당 위치부터 실행을 재개할 수 있어야한다.
- 다음에 어떤 코루틴을 실행할지 결정한다.

마지막 동작은 코루틴 컨텍스트에 있는 디스패처에 의해 수행된다. 일시 중단 함수를 컴파일하는 컴파일러는 앞의 두가지 작업을 할 수 있는 코드를 생성해 내야 한다. 이때 코틀린은 **컨티뉴에이션 패싱 스타일(CPS)** 변환과 **상태 기계**를 활용해 코드를 생성한다.

- CPS 변환은 프로그램의 실행 중 특정 시점 이후에 진행해야 하는 내용을 별도의 함수(컨티뉴에이션 함수)로 뽑고, 그 함수에게 현재 시점까지 실행한 결과를 넘겨서 처리하게 만드는 소스코드 변환 기술이다.

- CPS를 사용하는 경우 프로그램이 다음에 해야 할 일이 항상 컨티뉴에이션이라는 함수 형태로 전달되므로, 나중에 할 일을 명확히 알 수 있고, 그 컨티뉴에이션에 넘겨야 할 값이 무엇인지도 명확하게 알 수 있기 때문에 프로그램이 실행 중이던 특정 시점의 맥락을 잘 저장했다가 필요할 때 다시 재개할 수 있다.



## 4. 코루틴 빌러 만들기

### (1) 제네레이터 빌더 사용법

```kotlin
fun idMaker() = generate<Int, Unit> {
  var index = 0
  while( index < 3 )
  	yield(index++)
}
fun main(){
  val gen = idMaker()
  println(gen.next(Unit)) // 0
  println(gen.next(Unit)) // 1
  println(gen.next(Unit)) // 2
  println(gen.next(Unit)) // null
}
```

### (2) 제네레이터 빌더 구현

```kotlin
interface Generator<out R, in T> {
  fun next(param: T):R? // 제네레이터가 끝나면 null을 돌려주므로 ?가 붙음
}
```

