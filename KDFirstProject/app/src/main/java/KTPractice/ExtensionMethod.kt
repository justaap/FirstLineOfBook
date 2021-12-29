package KTPractice

class ExtensionMethod {
}

/**
 * 扩展函数测试：
 * 统计字符串中字母的数量
 * */

//传统方式：创建单例类
object StringUtil{
    fun letterCount(str: String): Int {
        var count = 0;
        for (char in str) {
            if (char.isLetter()) {
                count++
            }
        }
        return count
    }
}

//扩展函数的方式：
//自动拥有String示例的上下文，无需再接收参数，遍历本身即可（this）
fun String.lettersCount(): Int {
    var count= 0
    for (char in this) {
        if (char. isLetter()) {
            count++
        }
    }
    return count
}

fun main() {
    val str1="abc123xyz?#$#"
    val count1 = StringUtil.letterCount(str1)
    println("str1 has $count1 letters")

    val count2 = "abc123xyz#$@".lettersCount()//直接调用函数即可
    println("str2 hsa $count2 letters")

}

