dependencies {
    implementation(project(":home-system-api"))
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("mysql:mysql-connector-java:8.0.30")
    compileOnly("com.google.guava:guava:31.1-jre")
    compileOnly("com.google.code.gson:gson:2.10")
}