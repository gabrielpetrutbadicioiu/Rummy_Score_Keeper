package ro.gtechco.rummyscorekeeper.presentation

import android.net.Uri

sealed class MainScreenEvent {
    data object OnFabClick:MainScreenEvent()
    data object OnBottomSheetDismiss:MainScreenEvent()
    data object OnSaveBtnClick:MainScreenEvent()
    data object OnEditPlayerDismiss:MainScreenEvent()
    data object OnSaveEditBtnClick:MainScreenEvent()
    data object OnAlertDialogDismiss:MainScreenEvent()
    data object OnConfirmPlayerDelete:MainScreenEvent()

    data class OnScoreValueChange(val scoreValue:String):MainScreenEvent()
    data class OnUriResult(val uri: Uri?):MainScreenEvent()
    data class OnNewPlayerNameChange(val name:String):MainScreenEvent()
    data class OnEditPlayerClick(val index:Int):MainScreenEvent()
    data class OnEditedPlayerNameChange(val newName:String):MainScreenEvent()
    data class OnEditedPlayerUriResult(val uri: Uri?):MainScreenEvent()
    data class OnDeletePlayerClick(val index: Int):MainScreenEvent()
}