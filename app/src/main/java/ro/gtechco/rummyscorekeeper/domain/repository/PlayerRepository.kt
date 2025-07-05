package ro.gtechco.rummyscorekeeper.domain.repository


import kotlinx.coroutines.flow.Flow
import ro.gtechco.rummyscorekeeper.domain.model.Player

interface PlayerRepository {
    fun getAllPlayers(): Flow<List<Player>>

    suspend fun upsertPlayer(player: Player)

    suspend fun deletePlayer(player: Player)
}