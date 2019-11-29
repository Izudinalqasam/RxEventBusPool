fun main() {
    val student1 = StudentObj()
    val student2 = StudentObj()

    print(student1.equals(student2))
}

class StudentObj(){

    var name: String = ""
    get() = field
    set(value) {
        field = value
    }
}