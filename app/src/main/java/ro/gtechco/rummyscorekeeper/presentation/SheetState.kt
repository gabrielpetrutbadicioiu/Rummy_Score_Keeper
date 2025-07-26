package ro.gtechco.rummyscorekeeper.presentation


data class SheetState(
    val isScoreErr:Boolean=false,
    val isNameErr:Boolean=false,
    val isEditedNameErr:Boolean=false
)
