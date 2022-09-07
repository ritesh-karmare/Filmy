package rk.entertainment.filmy.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rk.entertainment.filmy.data.repository.MoviesRepositoryImpl
import rk.entertainment.filmy.data.network.APIService
import rk.entertainment.filmy.domain.repository.MoviesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: APIService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)
    }

}