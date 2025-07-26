package ro.gtechco.rummyscorekeeper.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gtechco.rummyscorekeeper.R
import ro.gtechco.rummyscorekeeper.presentation.composables.EditPlayerBottomSheetContent
import ro.gtechco.rummyscorekeeper.presentation.composables.PlayerItem
import ro.gtechco.rummyscorekeeper.presentation.composables.SheetContent
import ro.gtechco.rummyscorekeeper.presentation.core.DisplayLottie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainScreenViewModel)
{
val context= LocalContext.current
    LaunchedEffect(key1 = true) {
viewModel.eventFlow.collectLatest { event->
    when(event)
    {
        is MainScreenViewModel.MainScreenUiEvent.OnShowToast->{
            Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
        }
    }
}
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red),
                title ={
                    Row(modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(stringResource(R.string.main_screen_title),
                            fontSize = 32.sp,
                            fontFamily = FontFamily.Cursive,
                            fontWeight = FontWeight.Bold)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(8.dp),
                shape = CircleShape,
                containerColor = if(isSystemInDarkTheme()) Color.Transparent else Color.White,
                onClick = {viewModel.onEvent(MainScreenEvent.OnFabClick)}
            ) {
                DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.add_red), modifier = Modifier.size(70.dp))
            }
        }
    ){innerPadding->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top){
            if (viewModel.players.value.isEmpty())
            {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    DisplayLottie(
                        modifier = Modifier.size(256.dp),
                        spec = LottieCompositionSpec.RawRes(R.raw.empty)
                        )
                    Spacer(Modifier.height(8.dp))
                    Text(text = stringResource(R.string.empty_player_list),
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
            else{
                itemsIndexed(viewModel.players.value){index, player->
                   PlayerItem(
                       player = player,
                       onDeletePlayer = {viewModel.onEvent(MainScreenEvent.OnDeletePlayerClick(index=index))},
                       onEditPlayerClick = {viewModel.onEvent(MainScreenEvent.OnEditPlayerClick(index))}
                   )
                }
            }

        }
        if (viewModel.screenState.value.showBottomSheet)
        {
            ModalBottomSheet(onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnBottomSheetDismiss)}) {
                SheetContent(
                    isScoreError =viewModel.sheetState.value.isScoreErr ,
                    scoreValue = viewModel.newPlayer.value.maximumScore,
                    avatarImageUri = viewModel.newPlayer.value.profilePicture,
                    onScoreValueChange = {newScoreValue-> viewModel.onEvent(MainScreenEvent.OnScoreValueChange(newScoreValue))},
                    onUriResult = {uri-> viewModel.onEvent(MainScreenEvent.OnUriResult(uri))},
                    avatarSize = 128,
                    playerName = viewModel.newPlayer.value.name,
                    onPlayerNameChange = {name-> viewModel.onEvent(MainScreenEvent.OnNewPlayerNameChange(name))},
                    onCancelBtnClick = {viewModel.onEvent(MainScreenEvent.OnBottomSheetDismiss)},
                    onSaveBtnClick = {viewModel.onEvent(MainScreenEvent.OnSaveBtnClick)},
                    isNameError = viewModel.sheetState.value.isNameErr
                )
            }
        }
        if (viewModel.screenState.value.showEditPlayerBottomSheet)
        {
            ModalBottomSheet(
                onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnEditPlayerDismiss)}
                ) {
                EditPlayerBottomSheetContent(
                    player = viewModel.editedPlayer.value,
                    avatarSize = 128,
                    onCancelBtnClick = {viewModel.onEvent(MainScreenEvent.OnEditPlayerDismiss)},
                    onPlayerNameChange = {name->viewModel.onEvent(MainScreenEvent.OnEditedPlayerNameChange(name))},
                    onSaveBtnClick = {viewModel.onEvent(MainScreenEvent.OnSaveEditBtnClick)},
                    onUriResult = {uri ->  viewModel.onEvent(MainScreenEvent.OnEditedPlayerUriResult(uri))},
                    isNameError = viewModel.sheetState.value.isEditedNameErr
                )
            }
        }

        if (viewModel.screenState.value.showDeleteAlertDialog)
        {
            AlertDialog(
                onDismissRequest = {viewModel.onEvent(MainScreenEvent.OnAlertDialogDismiss)},
                confirmButton = {
                    Button(onClick = {viewModel.onEvent(MainScreenEvent.OnConfirmPlayerDelete)},
                        colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme())Color.Green else Color.Red)) {
                        Text(text = stringResource(R.string.confirm))
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick ={viewModel.onEvent(MainScreenEvent.OnAlertDialogDismiss)} ,
                        border = BorderStroke(width = 1.dp, color = if (isSystemInDarkTheme())Color.Green else Color.Red)
                    ) {
                        Text(text = stringResource(R.string.cancel),
                            color =if (isSystemInDarkTheme())Color.Green else Color.Red)
                    }
                },
                title = { Text(text = stringResource(R.string.delete_player_alert)) },
                text = { Text(text = stringResource(R.string.undone_warn)) }
                )
        }


    }
}