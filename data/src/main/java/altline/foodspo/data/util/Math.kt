package altline.foodspo.data.util

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

// Modified version of https://stackoverflow.com/a/41434490/6640693
fun Double.toFractionString(): String {
    if (this < 0) {
        return "-" + this.unaryMinus().toFractionString()
    }
    
    val wholePart = this.toInt()
    val remainder = this - wholePart
    
    if (remainder == 0.0) {
        return wholePart.toString()
    }
    
    val tolerance = 1.0E-6
    var h1 = 1.0
    var h2 = 0.0
    var k1 = 0.0
    var k2 = 1.0
    var b = remainder
    
    do {
        val a = floor(b)
        var aux = h1
        h1 = a * h1 + h2
        h2 = aux
        aux = k1
        k1 = a * k1 + k2
        k2 = aux
        b = 1 / (b - a)
    } while (abs(remainder - h1 / k1) > remainder * tolerance)
    
    return buildString {
        if (wholePart != 0) append("$wholePart ")
        append("${h1.toInt()}/${k1.toInt()}")
    }
}

fun Double.roundToNearest(x: Double): Double {
    return x * (round(this / x))
}