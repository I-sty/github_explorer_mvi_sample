package com.i_sz_app.githubexplorer.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@Configuration
@ComponentScan("com.i_sz_app.githubexplorer.data")
internal class DataModule

@Module
@Configuration
@ComponentScan("com.i_sz_app.githubexplorer.domain")
internal class DomainModule

@Module
@Configuration
@ComponentScan("com.i_sz_app.githubexplorer.presentation")
internal class PresentationModule
