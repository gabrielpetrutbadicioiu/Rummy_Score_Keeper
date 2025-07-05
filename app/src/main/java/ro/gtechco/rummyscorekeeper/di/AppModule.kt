package ro.gtechco.rummyscorekeeper.di

import androidx.room.Room
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ro.gtechco.rummyscorekeeper.core.DataBases
import ro.gtechco.rummyscorekeeper.data.data_source.PlayerDatabase
import ro.gtechco.rummyscorekeeper.data.repository.PlayerRepositoryImpl
import ro.gtechco.rummyscorekeeper.domain.repository.PlayerRepository
import ro.gtechco.rummyscorekeeper.presentation.MainScreenViewModel

val module= module{

    single {
        Room.databaseBuilder(
            get(),
            PlayerDatabase::class.java,
            DataBases.PLAYER_DB.name
        ).build().playerDao
    }
    single<PlayerRepository>{ PlayerRepositoryImpl(dao = get()) }

    viewModel { MainScreenViewModel(repository = get()) }

}