package racosta.samples.database.daos

import androidx.room.Dao
import androidx.room.Insert
import racosta.samples.database.model.RefundEntity

@Dao
interface RefundsDao {

    @Insert
    suspend fun insertAll(vararg refunds: RefundEntity)
}