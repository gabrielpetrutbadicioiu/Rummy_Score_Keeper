package ro.gtechco.rummyscorekeeper.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ro.gtechco.rummyscorekeeper.domain.model.Player

@Dao
interface PlayerDao {

    @Query("SELECT * FROM `player-table`")
    fun getAllPlayers():Flow<List<Player>>

    @Upsert
    suspend fun upsertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)
}