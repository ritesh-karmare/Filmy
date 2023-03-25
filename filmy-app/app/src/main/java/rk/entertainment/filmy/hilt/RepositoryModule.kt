package rk.entertainment.filmy.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rk.entertainment.data.network.APIService
import rk.entertainment.data.repository.MoviesRepositoryImpl
import rk.entertainment.domain.repository.MoviesRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
//    @Singleton
    fun provideMoviesRepository(apiService: APIService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)
    }

}