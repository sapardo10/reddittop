package com.paypal.redditop.di

import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.domain.GetAllPostUseCase
import com.paypal.redditop.domain.GetPostUseCase
import com.paypal.redditop.domain.IGetAllPostUseCase
import com.paypal.redditop.domain.IGetPostUseCase
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

    @Provides
    fun provideGetPostUseCase(
        postRepository: IPostRepository
    ): IGetPostUseCase {
        return GetPostUseCase(
            postRepository = postRepository
        )
    }
}