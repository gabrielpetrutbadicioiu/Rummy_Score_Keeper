package ro.gtechco.rummyscorekeeper.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import ro.gtechco.rummyscorekeeper.domain.model.Player

@Database(
    entities = [Player::class],
    version = 1
)
abstract class PlayerDatabase:RoomDatabase() {
    abstract val playerDao:PlayerDao
}