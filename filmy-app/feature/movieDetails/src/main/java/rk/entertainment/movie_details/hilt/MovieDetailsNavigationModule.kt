package rk.entertainment.movie_details.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rk.entertainment.common.navigation.MovieDetailsNavigation
import rk.entertainment.movie_details.MovieDetailsNavigationImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailsNavigationModule {

    @Provides
    @Singleton
    fun provideMovieDetailsNavigation(): MovieDetailsNavigation {
        return MovieDetailsNavigationImpl()
    }

}