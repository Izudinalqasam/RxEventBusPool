import org.greenrobot.eventbus.EventBus

fun main() {
    val ss = StudentBus()
    val clone1 = ss
    val clone2: StudentBus? = ss
    val clone3 = ss

    print(clone1 == clone2)
}


class StudentBus()