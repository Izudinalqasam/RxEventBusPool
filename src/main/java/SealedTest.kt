import io.reactivex.subjects.BehaviorSubject

fun main() {
//    val list = mutableListOf<Result>()
//
//    val res = Result.Success("kn")
//    val res2 = Result.Success(SealedStudentTest("tarjo"))
//
//    list.add(Result.Loading)
//    list.add(res)
//    list.add(res2)
//    list.add(Result.Error(Throwable(RuntimeException())))
//
//    list.forEach {
//        when(it){
//            is Result.Success<*> -> {
//                if (it.data is SealedStudentTest){
//                    val instance = it.data as SealedStudentTest
//                    println(instance.name)
//                }else {
//                    println(it.data)
//                }
//            }
//            is Result.Loading -> {
//                println("loading")
//            }
//            is Result.Error -> {
//                println(it.error.localizedMessage)
//            }
//        }
//    }

    // ======================================================
    // Access 1 (Primitiv tipe data)
    val eventBus = RxEvenBusPool()
    val rxBus = eventBus.getEVent<String>(RxEvenBusPool.PoolKey.LOGIN)

    rxBus.onNext("RxJava")

    rxBus.subscribe {
        println(it)
    }

    rxBus.onNext("RX Java Pool")

    val exBus2 = eventBus.getEVent<String>(RxEvenBusPool.PoolKey.LOGIN)
    exBus2.subscribe {
        println(it)
    }

    val rxBusInt = eventBus.getEVent<Int>(RxEvenBusPool.PoolKey.REGIS)
    rxBusInt.onNext(1)

    rxBusInt.subscribe {
        println(it)
    }

    // ======================================================
    // Access 2 (Object Tipe data)
    val myEventStudentBus = eventBus.getEVent<RxEvenBusPool.EventStudent>(RxEvenBusPool.PoolKey.STUDENT)
    myEventStudentBus.onNext(RxEvenBusPool.EventStudent("Karno"))

    val myEventStudentReceive = eventBus.getEVent<RxEvenBusPool.EventStudent>(RxEvenBusPool.PoolKey.STUDENT)
    myEventStudentReceive.subscribe {
        println(it.name)
    }

    myEventStudentBus.onNext(RxEvenBusPool.EventStudent("Patro"))
}

class RxEvenBusPool {

    data class EventStudent(val name: String)
    data class EventTeacher(val grade: String)


    private val rxMap = HashMap<PoolKey, BehaviorSubject<*>>()

    fun <T> getEVent(key: PoolKey): BehaviorSubject<T> {
        val subject: BehaviorSubject<*>? = rxMap[key]

        return if (subject == null){
            rxMap[key] =  BehaviorSubject.create<T>()
            return rxMap[key] as BehaviorSubject<T>
        }else {
            subject as BehaviorSubject<T>
        }
    }

    enum class PoolKey {
        LOGIN, REGIS, STUDENT
    }
}

sealed class Result {
    object Loading : Result()
    data class Success<T> (val data: T) : Result()
    data class Error(val error: Throwable)  : Result()
}

class SealedStudentTest(
        val name: String
)