import com.android.build.gradle.TestedExtension

fun TestedExtension.applyAndroidConfig( appId: String? = null ) {
    compileSdkVersion( Project.targetSdk )
    defaultConfig {
        appId?.let { applicationId = it }
        minSdkVersion( Project.minSdk )
        targetSdkVersion( Project.targetSdk )
        versionCode = Project.versionCode
        versionName = Project.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName( "main" ).java.srcDirs( "src/main/kotlin" )
        getByName( "test" ).java.srcDirs( "src/test/kotlin" )
        getByName( "androidTest" ).java.srcDirs( "src/androidTest/kotlin" )
    }
    compileOptions {
        sourceCompatibility = Project.jdkVersion
        targetCompatibility = Project.jdkVersion
    }
}