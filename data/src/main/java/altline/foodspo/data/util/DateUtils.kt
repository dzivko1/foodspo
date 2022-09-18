package altline.foodspo.data.util

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.*

fun Date.toLocalDate(): LocalDate = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
fun LocalDate.toDate(): Date = Date(this.toEpochDay() * 86400000)

fun Timestamp.toLocalDate(): LocalDate = toDate().toLocalDate()
fun LocalDate.toTimestamp(): Timestamp = Timestamp(toDate())

fun Timestamp.plusWeeks(weeksToAdd: Long) = toLocalDate().plusWeeks(weeksToAdd).toTimestamp()
fun Timestamp.minusWeeks(weeksToSubtract: Long) = toLocalDate().minusWeeks(weeksToSubtract).toTimestamp()

fun LocalDate.atStartOfWeek(): LocalDate = with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
fun Timestamp.atStartOfWeek(): Timestamp = toLocalDate().atStartOfWeek().toTimestamp()

fun Timestamp.atStartOfDay(): Timestamp = toLocalDate().toTimestamp()