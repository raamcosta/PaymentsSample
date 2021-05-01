package racosta.samples.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import racosta.samples.core.daoports.DatabaseApi
import racosta.samples.core.daoports.PaymentsDaoPort
import racosta.samples.core.daoports.RefundsDaoPort
import racosta.samples.database.adapters.PaymentsDaoAdapter
import racosta.samples.database.adapters.RefundsDaoAdapter
import racosta.samples.database.daos.PaymentsDao
import racosta.samples.database.daos.RefundsDao
import racosta.samples.database.model.PaymentEntity
import racosta.samples.database.model.RefundEntity

const val DB_NAME = "payments"
const val DB_VERSION = 1

@TypeConverters(Converters::class)
@Database(entities = [PaymentEntity::class, RefundEntity::class], version = DB_VERSION)
internal abstract class PaymentsDatabase : RoomDatabase() {

    internal abstract fun paymentsDao(): PaymentsDao

    internal abstract fun refundsDao(): RefundsDao
}

fun initDb(app: Application): DatabaseApi {
    return object: DatabaseApi {
        val room = Room.databaseBuilder(app, PaymentsDatabase::class.java, DB_NAME).build()

        override fun payments(): PaymentsDaoPort {
            return PaymentsDaoAdapter(room.paymentsDao())
        }

        override fun refunds(): RefundsDaoPort {
            return RefundsDaoAdapter(room.refundsDao())
        }
    }
}