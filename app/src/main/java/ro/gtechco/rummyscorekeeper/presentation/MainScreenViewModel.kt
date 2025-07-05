package ro.gtechco.rummyscorekeeper.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository

class MainScreenViewModel(
    private val repository: PlayerRepository
):ViewModel() {

    private val _players= mutableStateOf<List<Player>>(emptyList())
    val players:State<List<Player>> = _players
}