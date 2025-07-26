package ro.gtechco.rummyscorekeeper.domain.use_cases

import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository

class SavePlayerEdit(private val repository: PlayerRepository){
    suspend fun execute(
        player: Player,
        onFailure:(String)->Unit,
        onSuccess:()->Unit)
    {
        if (player.name.isEmpty())
        {
            onFailure("Player name is required!")
            return
        }
        repository.upsertPlayer(player)
        onSuccess()
    }
}