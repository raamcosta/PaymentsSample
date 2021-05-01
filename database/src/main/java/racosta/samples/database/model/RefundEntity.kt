package racosta.samples.database.model

import androidx.room.*
import java.math.BigDecimal
import java.util.*

@Entity
data class RefundEntity(
    @field:PrimaryKey(autoGenerate = true) val id: Int,
    val paymentId: Int,
    val amount: BigDecimal,
    val timestamp: Long
)