package Task1

interface Publication {
    val price: Double
    val wordCount: Int
    fun getType(): String

}

class Book(override val price: Double, override val wordCount: Int) : Publication {

    override fun getType(): String {

        if (wordCount <= 1000) {
            return "Flash Fiction"
        } else if (wordCount <= 7500) {
            return "Short Story"
        } else {
            return "Novel"
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Book && other.price == this.price && other.wordCount==this.wordCount
    }

    override fun toString(): String {
        return "Type: ${this.getType()}, word count: ${this.wordCount}, price: €${this.price}"
    }

    }

class Magazine(override val price: Double, override val wordCount: Int) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
    override fun toString(): String {
        return "Type: ${this.getType()}, word count: ${this.wordCount}, price: €${this.price}"
    }
}

fun comparison(a: Book, b: Book) {
    println("Сравнение")
    println(a === b)
    println(a == b)
}

fun buy(arg: Publication) {
    println("The purchase is complete. The purchase amount was €${arg.price}")
}

var sum = { a: Double, b: Double ->
    val c = a + b
    println("sum = $c")
    c
}

fun books() {
    val aBriefHistoryOfTime = Book(99.99, 15000)
    val foundation = Book(50.4, 7500)
    val time = Magazine(10.2, 889)


    println(aBriefHistoryOfTime)
    println(foundation)
    println(time)

    comparison(foundation, aBriefHistoryOfTime)

    buy(time)

    var nullBook1: Book?
    var nullBook2: Book?
    nullBook1 = null
    nullBook2 = foundation

    try {
        buy(nullBook1!!)
    } catch (e: Exception) {
        println("Вот тут упало. Ошибка: ${e}")
    }
    buy(nullBook2)

    sum(3.14, 9.8)
}