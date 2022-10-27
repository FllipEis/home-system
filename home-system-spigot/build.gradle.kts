repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation(project(":home-system-api"))
    implementation(project(":home-system-common"))
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("mysql:mysql-connector-java:8.0.30")
}