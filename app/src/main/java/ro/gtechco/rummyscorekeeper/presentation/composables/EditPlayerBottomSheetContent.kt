package ro.gtechco.rummyscorekeeper.presentation.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun EditPlayerBottomSheetContent(
    player: Player,
    avatarSize:Int,
    onUriResult:(Uri?)->Unit,
    onPlayerNameChange:(String)->Unit,
    isNameError:Boolean,
    onCancelBtnClick:()->Unit,
    onSaveBtnClick:()->Unit)
{
 val avatarPhotoPicker= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia())
 {uri ->
     onUriResult(uri)
 }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.edit_player_text),
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
            if (player.profilePicture.isNotEmpty())
            {
                AsyncImage(
                    model = player.profilePicture,
                    contentDescription = stringResource(R.string.avatar_img_description),
                    modifier = Modifier.size(avatarSize.dp).clip(shape = CircleShape) ,
                    contentScale = ContentScale.Crop)
            }
            else{
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.dog_avatar))
                val progress by animateLottieCompositionAsState(
                    composition =composition,
                    isPlaying =true,
                    iterations = LottieConstants.IterateForever
                )
                Box {
                    LottieAnimation(composition = composition, progress = progress, modifier = Modifier.size(128.dp))
                }
            }
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = if(isSystemInDarkTheme()) Color.Green else Color.Red),
                onClick = {
                    avatarPhotoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = stringResource(R.string.player_picture))
                    Spacer(Modifier.width(2.dp))
                    Icon(imageVector = Icons.Outlined.AddAPhoto, contentDescription = stringResource(R.string.icon_description))
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                value =player.name,
                onValueChange = {newName-> onPlayerNameChange(newName.replaceFirstChar { char-> char.uppercase() }) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color.Green),
                isError = isNameError,
                label = {Text(text = stringResource(R.string.enter_player_name))},
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround) {

                OutlinedButton(onClick = {onCancelBtnClick()},
                    border = BorderStroke(2.dp, if (isSystemInDarkTheme()) Color.Green else Color.Red)
                ) {
                    Text(text = stringResource(R.string.cancel),
                        color =if (isSystemInDarkTheme()) Color.Green else Color.Red )
                }
                Button(onClick = {onSaveBtnClick()},
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Color.Green else Color.Red)
                ) {
                    Text(text = stringResource(R.string.save))
                }

            }
    }
}

