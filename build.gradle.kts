plugins {
    java
}

group = "com.anantha.rocksdb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.rocksdb:rocksdbjni:9.1.1")

    testImplementation("org.assertj:assertj-core:3.26.3")

    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()

    systemProperties["rocksdb_dir"] = projectDir.resolve("rocksdb-data").absolutePath
}