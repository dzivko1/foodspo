package altline.foodspo.data.ingredient.model

import altline.foodspo.data.util.roundToNearest
import altline.foodspo.data.util.toFractionString

data class Measure(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
) {
    override fun toString(): String {
        val unit = if (amount <= 1) unitShort else unitLong
        val rounded =
            if (unitLong == "milliliters" ||
                unitLong == "grams"
            ) amount.roundToNearest(50.0)
            else amount
        return "${rounded.toFractionString()} $unit"
    }
}
