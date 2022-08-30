package altline.foodspo.util

fun <E> List<E>.replaceAt(index: Int, element: E) =
    toMutableList().apply { this[index] = element }

fun <E> List<E>.minusAt(index: Int) = filterIndexed { i, _ -> i != index }