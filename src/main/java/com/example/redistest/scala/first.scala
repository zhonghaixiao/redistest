object Test{
  def main(args: Array[String]): Unit = {
    case class Student(name: String, score: Float)
    val han = Student("zhong", 26.5f)
    println(s"${han.name} has a score of ${han.score}")
    println(f"${han.name} has a score of ${han.score}%.3f pounds")
  }
  def printArray(array: Array[Int]) = {
    for (a <- array) print(a)
    println()
  }
  def addInt(a:Int, b:Int) : Int = {
    var sum:Int = 0
    sum = a + b
    return sum
  }
  def time() = {
    println("获取时间，单位纳秒")
    System.nanoTime
  }
  def delayed(t: => Long) = {
    println("在delayed 方法内")
    println("参数 ： " + t)
    t
  }
}