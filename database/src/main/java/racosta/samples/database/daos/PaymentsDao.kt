package racosta.samples.database.daos

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import racosta.samples.database.model.PaymentEntity
import racosta.samples.database.model.PaymentWithRefundsEntity

@Dao
interface PaymentsDao {
    @WorkerThread
    @Insert
    suspend fun insertAll(vararg payments: PaymentEntity?)

    @Transaction
    @Query("SELECT * FROM PaymentEntity ORDER BY timestamp DESC")
    fun observePaymentsWithRefunds(): Flow<List<PaymentWithRefundsEntity>>

    @Transaction
    @Query("SELECT * FROM PaymentEntity WHERE id = :id")
    fun observePaymentWithRefunds(id: Int): Flow<PaymentWithRefundsEntity>

    @WorkerThread
    @Transaction
    @Query("SELECT * FROM PaymentEntity WHERE id = :id")
    suspend fun getPaymentWithRefunds(id: Int): PaymentWithRefundsEntity?
}