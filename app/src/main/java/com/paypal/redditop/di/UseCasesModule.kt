package com.paypal.redditop.di

import com.paypal.data.repositories.IPostRepository
import com.paypal.domain.GetAllPostUseCase
import com.paypal.domain.IGetAllPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideGetAllPostsUseCase(
        postRepository: IPostRepository
    ): IGetAllPostUseCase {
        return GetAllPostUseCase(
            postRepository = postRepository
        )
    }
}