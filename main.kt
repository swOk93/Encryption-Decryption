package encryptdecrypt
import java.io.File

var mode = "enc"
var key = 0
var data = ""
var out = ""
var alg = "shift"
val list = mutableListOf <Char> ()

fun printRes() {
    if (out.isNotEmpty()) {
        File(out).writeText(list.joinToString(""))
    } else println(list.joinToString(""))
}

fun shiftEncDec() {
    if (mode == "dec") key = -key
    for (ch in data) {
        var first = 97
        var last = 123
        if (ch.code in 65 until 91) {
            first = 65
            last = 91
        }
        if (ch.code in first until last) {
            if (key >= 0 && (ch.code + key) < last) {
                list.add((ch.code + key).toChar())
            }
            else if (key < 0 && (ch.code + key) >= first) {
                list.add((ch.code + key).toChar())
            }
            else {
                if (key >= 0) list.add((first + (ch.code + key - last)).toChar())
                else list.add((last - (first - (ch.code + key))).toChar())
            }
        }
        else list.add(ch)
    }
    printRes()
}

fun unicodeEncDec() {
    val str = data
    val n = key
    when (mode) {
        "enc" -> for (ch in str) {
            list.add((ch.code + n).toChar())
        }
        "dec" -> for (ch in str) {
            list.add((ch.code - n).toChar())
        } else -> println("something wrong")
    }
    printRes()
}

fun main(args: Array<String>) {
    for (i in args.indices) {
        if (args[i][0] == '-') {
            when (args[i]) {
                "-mode" -> mode = args[i + 1]
                "-key" -> key = args[i + 1].toInt()
                "-in" -> data = File(args[i + 1]).readText()
                "-data" -> data = args[i + 1]
                "-out" -> out = args[i + 1]
                "-alg" -> alg = args[i + 1]
                else -> println("Something wrong")
            }
        }
    }
    when (alg) {
        "shift" -> shiftEncDec()
        "unicode" -> unicodeEncDec()
        else -> println("Something wrong")
    }
}
