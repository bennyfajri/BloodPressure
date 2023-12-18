import org.gradle.api.artifacts.dsl.DependencyHandler

object SharedDependencies {
    fun DependencyHandler.applySharedDeps() {
        //default android
        add("implementation", Deps.coreKtx)
        add("implementation", Deps.appCompat)
        add("implementation", Deps.materialDesign)
        add("implementation", Deps.constraintLayout)
        add("testImplementation", Deps.Testing.jUnit)
        add("androidTestImplementation", Deps.Testing.extJUnit)
        add("androidTestImplementation", Deps.Testing.espresso)
        add("implementation", Deps.multidex)

        //Coroutine
        add("implementation", Deps.KotlinX.coroutineAndroid)
        add("implementation", Deps.KotlinX.coroutineCore)

        //KTX
        add("implementation", Deps.activityKtx)
        add("implementation", Deps.fragmentKtx)
        add("implementation", Deps.lifecycleViewModel)
        add("implementation", Deps.lifecycleLiveData)

        //navigation KTX
        add("implementation", Deps.navigationUiKtx)
        add("implementation", Deps.navigationFragmentKtx)

        //Hilt
        add("implementation", Deps.DaggerHilt.hilt)
        add("kapt", Kapt.hiltCompiler)

        //Glide
        add("implementation", Deps.glide)
    }
}