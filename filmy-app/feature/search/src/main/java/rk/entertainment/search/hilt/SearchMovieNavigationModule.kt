package rk.entertainment.search.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rk.entertainment.common.navigation.SearchMovieNavigation
import rk.entertainment.search.navigation.SearchMovieNavigationImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchMovieNavigationModule {

    @Provides
    @Singleton
    fun provideSearchMovieNavigation(): SearchMovieNavigation {
        return SearchMovieNavigationImpl()
    }

}