import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject

fun main() {
   rxNullExample()
}

fun rxNullExample(){
    var studentNull: StudentNull? = null

    Observable.just(studentNull?.name)
            .subscribe({
                print(it)
            },{
                print("error nih")
            })
}

data class StudentNull(val name: String)

fun behaviourSubjectExample(){
    val KEY_SEARCH = "KEY_SEARCH"
    val behaviour = BehaviorSubject.create<Int>()

    val rxPool = HashMap<String, BehaviorSubject<Int>>()
    rxPool[KEY_SEARCH] = behaviour


    //=========================================
    val behav1 = rxPool[KEY_SEARCH]
    behav1?.onNext(1)
    behav1?.onNext(2)

    behav1?.subscribe {
        print(it)
    }
}

fun singleSubjectExample(){
    val singleSubject = SingleSubject.create<Int>()

    singleSubject.onSuccess(1)
    singleSubject.onSuccess(2)

    singleSubject.subscribe({
        print(it)
    },{

    })
}

fun mergeExample(){
    val subject1 = PublishSubject.create<Int>()
    val subject2 = PublishSubject.create<Int>()

    val result = Observable.merge(subject1, subject2)
            .subscribeOn(Schedulers.trampoline())

    printMergeExample(result)

    subject1.onNext(1)
    subject2.onNext(2)

    Thread.sleep(2000)
    subject1.onNext(3)
}

fun printMergeExample(merged: Observable<Int>){
    merged.subscribe {
        println(it)
    }
}



fun caching(nilai: Int){
    val emitter1 = Observable.fromCallable { nilai }// cache
    val emitter2 = Observable.just(2)// remote

    emitter1
            .flatMap {
        if (it != 0) {
            return@flatMap Observable.just(it)
        } else {
            return@flatMap emitter2
        }}
            .subscribeOn(Schedulers.trampoline())
            .subscribe {
                print(it)
            }
}

fun <T> toObservable(nilai: T): Observable<T>{
    return Observable.fromCallable { nilai }
}

fun concatRxJava(){
    val emitter1 = Observable.just(1)
    val emitter2 = Observable.just(2)

    Observable.concat(emitter1, emitter2)
            .first(0)
            .subscribeOn(Schedulers.trampoline())
            .subscribe({
                print(it)
            },{

            })
}

fun chainRXjava(){
    Observable.just(1, 2, 3)
            .startWith(0)
            .cast(Number::class.java)
            .subscribeOn(Schedulers.trampoline())
            .doOnNext { println("Angka ke - ") }
            .subscribe {
                print("$it \n")
            }
}

fun completableExample(){
    Completable.fromAction {
        doSomething()
    }.andThen(Observable.just(1, 2))
            .subscribeOn(Schedulers.trampoline())
            .subscribe {
                print("$it \n")
            }
}

fun mergeOperation(){
    Observable.merge<Int> {
        ObservableTransformer<String, Int>{
            it.map { str -> str.toInt() }
        };
        ObservableTransformer<String, Int>{
            it.map { str -> str.toInt() }
        }
    }

    Observable.merge(
            Observable.just(1, 2, 3),
            Observable.just("4", "5")
    )
            .subscribeOn(Schedulers.trampoline())
            .subscribe {
                if (it is String){
                    print("Kata $it \n")
                }else {
                    print("Angka $it \n")
                }
            }
}

fun doSomething(){

}