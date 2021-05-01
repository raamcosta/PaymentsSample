package racosta.samples.database.model

import androidx.room.*
import java.math.BigDecimal
import java.util.*

@Entity
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val cardHolder: String,
    val cardNumber: Long,
    val cvv: Int,
    val amount: BigDecimal,
    val expDateMonth: Int,
    val expDateYear: Int,
    val timestamp: Long
)