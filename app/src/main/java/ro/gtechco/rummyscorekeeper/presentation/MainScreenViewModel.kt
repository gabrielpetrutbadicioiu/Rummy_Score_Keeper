package ro.gtechco.rummyscorekeeper.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository
import ro.gtechco.rummyscorekeeper.domain.use_cases.MainScreenUseCases

class MainScreenViewModel(
    private val repository: PlayerRepository,
    private val useCases: MainScreenUseCases
):ViewModel() {

    //states
    private val _players= mutableStateOf<List<Player>>(emptyList())
    val players:State<List<Player>> = _players

    private val _newPlayer= mutableStateOf(Player())
    val newPlayer:State<Player> =_newPlayer

    private val _screenState= mutableStateOf(MainScreenState())
    val screenState:State<MainScreenState> =_screenState

    private val _sheetState= mutableStateOf(SheetState())
    val sheetState:State<SheetState> = _sheetState

    private val _editedPlayer= mutableStateOf(Player())
    val editedPlayer:State<Player> = _editedPlayer

    //one time events
    private val _eventFlow= MutableSharedFlow<MainScreenUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()


    sealed class MainScreenUiEvent{
        data class OnShowToast(val message:String):MainScreenUiEvent()
    }
    init {
        repository.getAllPlayers().onEach { playerList->
            _players.value=playerList
        }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: MainScreenEvent)
    {
        when(event)
        {
            is MainScreenEvent.OnFabClick->
            {
                _screenState.value=_screenState.value.copy(showBottomSheet = true)
                if (_players.value.isNotEmpty()) _newPlayer.value=_newPlayer.value.copy(maximumScore = _players.value[0].maximumScore)
            }
            is MainScreenEvent.OnBottomSheetDismiss->{
                _newPlayer.value=Player()
                _screenState.value=_screenState.value.copy(showBottomSheet = false)
                _sheetState.value=SheetState()
            }
            is MainScreenEvent.OnScoreValueChange->{
                _newPlayer.value=_newPlayer.value.copy(maximumScore = event.scoreValue)
            }
            is MainScreenEvent.OnUriResult->{
                _newPlayer.value=_newPlayer.value.copy(profilePicture = if (event.uri==null) "" else event.uri.toString())
            }
            is MainScreenEvent.OnNewPlayerNameChange->{
                _newPlayer.value=_newPlayer.value.copy(name = event.name)
            }
            is MainScreenEvent.OnSaveBtnClick->{
                try {
                    viewModelScope.launch {
                        useCases.addNewPlayer.execute(
                            newPlayer = _newPlayer.value,
                            onScoreErr = {e->
                                _sheetState.value=_sheetState.value.copy(isScoreErr = true)
                                viewModelScope.launch { _eventFlow.emit(MainScreenUiEvent.OnShowToast(e)) }
                            },
                            onNameErr = {e->
                                _sheetState.value=_sheetState.value.copy(isNameErr = true, isScoreErr = false)
                                viewModelScope.launch { _eventFlow.emit(MainScreenUiEvent.OnShowToast(e)) }
                            },
                            onSuccess = {
                                _newPlayer.value=Player()
                                _screenState.value=_screenState.value.copy(showBottomSheet = false)
                                _sheetState.value=_sheetState.value.copy(isNameErr = false, isScoreErr = false)
                            }
                        )
                    }
                }catch (e:Exception)
                {
                    viewModelScope.launch { _eventFlow.emit(MainScreenUiEvent.OnShowToast(message = e.message?:"muie"))}
                }
            }
            is MainScreenEvent.OnEditPlayerClick->{
                _editedPlayer.value=_players.value[event.index].copy()
                _screenState.value=_screenState.value.copy(showEditPlayerBottomSheet = true)
            }
            is MainScreenEvent.OnEditPlayerDismiss->{
                _editedPlayer.value=Player()
                _screenState.value=_screenState.value.copy(showEditPlayerBottomSheet = false)
            }
            is MainScreenEvent.OnEditedPlayerNameChange->{
                _editedPlayer.value=_editedPlayer.value.copy(name = event.newName)
            }
            is MainScreenEvent.OnEditedPlayerUriResult->{
                _editedPlayer.value=_editedPlayer.value.copy(profilePicture = if (event.uri==null) "" else event.uri.toString())
            }
            is MainScreenEvent.OnSaveEditBtnClick->{
                viewModelScope.launch {
                    useCases.savePlayerEdit.execute(
                        player = _editedPlayer.value,
                        onFailure = {e->
                            viewModelScope.launch { _eventFlow.emit(MainScreenUiEvent.OnShowToast(e)) }
                        },
                        onSuccess = {
                            _editedPlayer.value=Player()
                            _sheetState.value=_sheetState.value.copy(isEditedNameErr = false)
                            _screenState.value=_screenState.value.copy(showEditPlayerBottomSheet = false)
                        }
                    )
                }
            }
            is MainScreenEvent.OnDeletePlayerClick->{
                _editedPlayer.value=_players.value[event.index].copy()
                _screenState.value=_screenState.value.copy(showDeleteAlertDialog = true)
            }
            is MainScreenEvent.OnAlertDialogDismiss->{
                _editedPlayer.value=Player()
                _screenState.value=_screenState.value.copy(showDeleteAlertDialog = false)
            }
            is MainScreenEvent.OnConfirmPlayerDelete->{
                viewModelScope.launch {
                    repository.deletePlayer(_editedPlayer.value.copy())
                }
                _editedPlayer.value=Player()
                _screenState.value=_screenState.value.copy(showDeleteAlertDialog = false)

            }
        }
    }
}