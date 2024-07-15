package com.example.test.di.module

import dagger.Module

@Suppress("unused")
@Module(includes = [PersistenceModule::class])
interface RepositoryModule
