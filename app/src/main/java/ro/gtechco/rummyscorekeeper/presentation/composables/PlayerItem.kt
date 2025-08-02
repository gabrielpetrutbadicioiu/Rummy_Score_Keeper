package ro.gtechco.rummyscorekeeper.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ro.gtechco.rummyscorekeeper.R
import ro.gtechco.rummyscorekeeper.domain.model.Player
import ro.gtechco.rummyscorekeeper.presentation.core.DisplayLottie

@Composable
fun PlayerItem(
    player: Player,
    onDeletePlayer:()->Unit,
    onEditPlayerClick:()->Unit)
{
    Spacer(modifier = Modifier.height(16.dp))
    Surface (modifier = Modifier
        .fillMaxWidth()
        .padding(start = 6.dp, end = 6.dp)
        .border(border = BorderStroke(
            width = 4.dp,
            brush = SolidColor(if (isSystemInDarkTheme()) Color.Green else Color.Red)
        ),
            shape = RoundedCornerShape(16.dp)
        ))
    {
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                if (player.profilePicture.isNotEmpty())
                {
                    Row {
                        Spacer(modifier = Modifier.width(12.dp))
                        AsyncImage(
                            model = player.profilePicture,
                            contentDescription = stringResource(R.string.avatar_img_description),
                            modifier = Modifier.size(64.dp)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                }
                else {
                    val dogComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.dog_avatar))
                    val progress by animateLottieCompositionAsState(
                        composition = dogComposition,
                        isPlaying = true,
                        iterations = LottieConstants.IterateForever
                    )
                    LottieAnimation(composition = dogComposition, progress = progress, modifier = Modifier.size(64.dp). padding(start = 16.dp))
                }

                Text(
                    text = player.name,
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp
                )

                Row {
                    IconButton(onClick = {onEditPlayerClick()}) {
                        Icon(imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.icon_description))
                    }
                    IconButton(onClick ={onDeletePlayer()} ) {
                        Icon(imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.icon_description))
                    }

                }

            }
            val snailComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.snail_loader))
            val snailProgress by animateLottieCompositionAsState(
                composition = snailComposition,
                isPlaying = true,//todo
                iterations = LottieConstants.IterateForever
            )
            LottieAnimation(composition = snailComposition, progress = snailProgress, modifier = Modifier.size(150.dp))


                BadgedBox(badge = {
                    //todo
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.AddCircle,
                            contentDescription = stringResource(R.string.icon_description),
                            tint = if (isSystemInDarkTheme()) Color.Green else Color.Red)
                    }
                }) {
                    Text(text = "${player.score}/${player.maximumScore}",
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = if (isSystemInDarkTheme()) Color.Green else Color.Red,
                            shape = RoundedCornerShape(16.dp)
                        ).padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            Text(text = "${stringResource(R.string.games_won)} ${player.gamesWon}",
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp)
            Spacer(modifier = Modifier.height(6.dp))





        }//column

    }
}