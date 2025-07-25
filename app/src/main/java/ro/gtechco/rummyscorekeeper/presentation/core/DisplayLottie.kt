package ro.gtechco.rummyscorekeeper.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun DisplayLottie(
     spec: LottieCompositionSpec,
     modifier: Modifier
)
{
    val composition by rememberLottieComposition(spec = spec)
    val progress by animateLottieCompositionAsState(
        composition =composition,
        isPlaying =true,
        iterations = LottieConstants.IterateForever
    )
    Box {
        LottieAnimation(composition = composition, progress = progress, modifier = modifier)
    }

}