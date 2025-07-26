package ro.gtechco.rummyscorekeeper.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player-table")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    @ColumnInfo(name = "name")
    val name:String="",
    @ColumnInfo(name = "profile_picture")
    val profilePicture: String="",
    @ColumnInfo("score")
    val score:Int=0,
    @ColumnInfo(name = "games-won")
    val gamesWon:Int=0,
    @ColumnInfo(name = "maximumScore")
    val maximumScore:String=""
)
