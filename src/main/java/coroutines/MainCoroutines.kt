package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

fun calculateNumber(){
    print("calculate function is run")
}

suspend fun fetchUser(user: User){
    // it is same with withContext {}
    GlobalScope.async(Dispatchers.IO) {

    }.await()

    return withContext(Dispatchers.IO) {

    }
}

fun fetchUserAndSaveInDatabase(){
    // fetch user from network
    // save user in database
    // and do not return anything
}

suspend fun fetchFirstUser(){
    withContext(NonCancellable){

    }
}

suspend fun fetchSecondUser(){}

// lambda function to handle exception
// we can add this in the launch parameter or async, or scope
val handlerLambda = CoroutineExceptionHandler { coroutineContext, throwable ->
    // do something
}

fun main() = runBlocking  {
    // use global scope when we need the global scope which is application scope, not the activity scope
    GlobalScope.launch {
        calculateNumber()
    }

    // because fetchUserAndSaveInDatabase() doesn't return anything we call the function with launch
    GlobalScope.launch(Dispatchers.IO + handlerLambda){
        fetchUserAndSaveInDatabase()
    }


    GlobalScope.launch(Dispatchers.Main) {
        val userOne = async(Dispatchers.IO) { fetchUser(User()) }
        val userTwo = async(Dispatchers.IO) { fetchSecondUser() }
        val userThree = async(Dispatchers.IO) { fetchFirstUser() }

        showUser(userOne, userTwo) // it will run on UI Thread

        // wait coroutines with specific time, if more than time will return null
        withTimeoutOrNull(5000){
            userOne.await()
        }
    }

    val job = SupervisorJob()



    // to handle exception when using method async you can use try catch
    try {
        GlobalScope.async() { fetchFirstUser() }.await()
    }catch (e: Exception){

    }

}

fun showUser(user1: Deferred<Unit>, user2: Deferred<Unit>){

}

class User

// this class is like Main Activiy in android
// using CoroutineScope to scope your coroutine, so when the activity get destroyed the coroutine job (background task) is canceled
class MainActivity() : CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handlerLambda

    private fun onCreate(){
        job = Job() // create job
    }

    private fun onDestroyed(){
        //As soon as the activity is destroyed, the task will get cancelled if it is running because we have defined the scope.
        job.cancel() // cancel job
    }
}