plugins {
    id("logfox.android.feature.compose")
}

android.namespace = "com.f0x1d.logfox.feature.apps.picker"

dependencies {
    implementation(projects.feature.appsPicker.api)

    implementation(libs.coil.compose)
    implementation(libs.androidx.compose.foundation) // Asegura que foundation esté correctamente importado
}
