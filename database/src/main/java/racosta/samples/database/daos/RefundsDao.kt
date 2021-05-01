package racosta.samples.database.daos

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import racosta.samples.database.model.RefundEntity

@Dao
interface RefundsDao {
    @WorkerThread
    @Insert
    fun insertAll(vararg refunds: RefundEntity?)
}