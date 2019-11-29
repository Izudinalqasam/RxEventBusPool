package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking<Unit> {
    //    val async1 = async { getData() }
//
//    val nm: Int = async1.await()
//    println(nm)

    //==================================
//    launch {
//        inits().map { it + it }.collect {
//            println("print coroutine1 $it")
//        }
//    }
//
//    launch {
//        delay(1000)
//        println()
//        inits().collect { println("print coroutine2 $it") }
//    }


    //=================================
//    simpleFlowOf().collect {
//        print(it)
//    }

//    val myChannerl = Channel<Int>()
//
//    launch {
//        simpleChannel(myChannerl)
//    }
//
//    consuming1(myChannerl).await()
//    consuming2(myChannerl).await()

    //=================================

//    runBlocking {
//        for (i in 1..2){
//            launch {
//                pendingAction()
//            }
//
//        }
//
//    }
//
//    print("execute first")
//    val mn: ReceiveChannel<Int> = actorExample()

//    val directChannel = produce {
//        for (i in 1..5){
//            send(i)
//        }
////    }
//
//    launch {
//        kotlinx.coroutines.delay(52)
//        consuming2(mn)
//    }
//
//    launch {
//        kotlinx.coroutines.delay(50)
//        consuming1(mn)
//    }

//    n.join()

    //================================================

//    val jobQU = launch(NonCancellable) {
//         for (i in 1..5){
//                kotlinx.coroutines.delay(1000)
//                println("State $i")
//            }
//    }
//
//    println("the task has been finished")
//    delay(2000)
//    jobQU.cancel()

    // ================================================
//    channelFlow {
//        for (i in 1..5) send(i)
//    }.map {
//        it * it
//    }.collect {
//        println(it)
//    }
//
//    flowOf(1, 2, 3)
//            .map { it - 1 }
//            .onStart { println("start value emmit") }
//            .onEach { println("the Value is below") }
//            .onCompletion { println("The emmited completed") }
//            .collect {
//                println(it)
//            }
//
//    flow {
//        emit(1)
//        emit(2)
//    }

//    listOf(1, 2, 3, 5).asFlow()
//            .toMyString {
//                "$it to string"
//            }
//            .collect {
//                println(it)
//            }

    flowOf( 1, 3, 3 )
            .map { throw ArrayIndexOutOfBoundsException() }
            .catch {
                println("The error has been handled")
            }
            .collect {
                print("Done")
            }

}

fun <T> Flow<T>.toIndo(): Flow<String> {
    return flow {
        collect {
            emit("${it.toString()} indo")
        }
    }
}

inline fun <T> Flow<T>.toMyString(
        crossinline transformVal: suspend (T) -> String
): Flow<String>{
    return transform {
        return@transform emit(transformVal(it))
    }
}


suspend fun actorExample() =
        CoroutineScope(Dispatchers.Default).produce {
            for (i in 1..5){
                send(i)
            }
        }


suspend fun pendingAction(){
    delay(2000)
    println("execute second")
}

suspend fun consuming1(channel: ReceiveChannel<Int>) {
    channel.consumeEach {
        println("coroutine 1 $it")
    }
}

suspend fun consuming2(channel: ReceiveChannel<Int>){
    channel.consumeEach {
        println("coroutine 2 $it")
    }
}

suspend fun simpleChannel(myChannerl: SendChannel<Int>){
    for (i in 1..5){
        myChannerl.send(i)
    }

    myChannerl.close()
}

fun simpleFlowOf(): Flow<Int>{
    return flowOf(1, 2, 3, 5)
            .map { it * it }
}



fun inits(): Flow<Int> = flow {
    for (i in 1..100){
        delay(100)
        emit(i)
    }
}

fun getData(): Int{
    return 5
}

class UsingCustomScope: CoroutineScope {

    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->

    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO + CoroutineName("") + handler
}
