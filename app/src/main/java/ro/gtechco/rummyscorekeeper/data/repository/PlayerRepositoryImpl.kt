package ro.gtechco.rummyscorekeeper.data.repository

import kotlinx.coroutines.flow.Flow
import ro.gtechco.rummyscorekeeper.data.data_source.PlayerDao
import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
    private val dao: PlayerDao
):PlayerRepository {
    override fun getAllPlayers(): Flow<List<Player>> {
        return dao.getAllPlayers()
    }

    override suspend fun upsertPlayer(player: Player) {
        dao.upsertPlayer(player)
    }

    override suspend fun deletePlayer(player: Player) {
       dao.deletePlayer(player)
    }
}