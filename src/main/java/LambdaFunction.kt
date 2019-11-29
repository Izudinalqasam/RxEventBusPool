fun main() {
    name("Jordan") { n, m ->
        print("$n dan $m")
    }

    val std = Student().customStudent {
        setName("norma")
    }

    print(std.studentName)

    val tryClassAbs: (String) -> Unit = {
        print(it)
    }

    tryClassAbs("Ruangguru")

    val dualGenericfunc = fakeMap<String, Int> {
        it.toInt()
    }

    val dualGenericVal: (String) -> Int = {
        0
    }

    print(dualGenericfunc)
}

inline fun <T,R> fakeMap (params: (T) -> R): R{
    return params("0" as T)
}

inline fun <T> classAbs(classNam: T, name: (T, Int) -> Unit){
    name.invoke(classNam, 20)
}


inline fun <T> name(classNam: T, name: (T, Int) -> Unit){
    name.invoke(classNam, 20)
}

inline fun <T> T.customStudent(applied: T.() -> Unit): T{
    applied()
    return this
}


class Student {
    var studentName = ""
    get() = field

    fun setName(nn: String){
        studentName = nn
    }
}