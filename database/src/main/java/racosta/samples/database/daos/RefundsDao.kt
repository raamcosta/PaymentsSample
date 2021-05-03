package racosta.samples.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import racosta.samples.database.model.RefundEntity

@Dao
interface RefundsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg refunds: RefundEntity)
}