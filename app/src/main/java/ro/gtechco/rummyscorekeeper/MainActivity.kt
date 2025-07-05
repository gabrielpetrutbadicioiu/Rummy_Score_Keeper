package ro.gtechco.rummyscorekeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gtechco.rummyscorekeeper.presentation.MainScreen
import ro.gtechco.rummyscorekeeper.presentation.MainScreenViewModel
import ro.gtechco.rummyscorekeeper.ui.theme.RummyScoreKeeperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RummyScoreKeeperTheme {
                val viewModel=getViewModel<MainScreenViewModel> ()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
