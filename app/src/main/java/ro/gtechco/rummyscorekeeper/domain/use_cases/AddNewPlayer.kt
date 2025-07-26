package ro.gtechco.rummyscorekeeper.domain.use_cases

import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository

class AddNewPlayer(
    private val repository: PlayerRepository
) {
    suspend fun execute(
        newPlayer: Player,
        onScoreErr:(String)->Unit,
        onNameErr:(String)->Unit,
        onSuccess:()->Unit
                        )
    {
        if (newPlayer.maximumScore.isEmpty())
        {
            onScoreErr("Winning score is required!")
            return
        }
        if (newPlayer.name.isEmpty())
        {
            onNameErr("Player name is required!")
            return
        }
        repository.upsertPlayer(newPlayer)
        onSuccess()
    }
}