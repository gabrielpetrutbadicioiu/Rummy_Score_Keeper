package ro.gtechco.rummyscorekeeper.presentation

data class MainScreenState(
    val showBottomSheet:Boolean=false,
    val showEditBottomSheet:Boolean=false,
    val showEditPlayerBottomSheet:Boolean=false,
    val showDeleteAlertDialog:Boolean=false
)
