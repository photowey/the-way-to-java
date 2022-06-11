fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.6.0"
    id("org.jetbrains.changelog") version "1.3.1"
}

group = "com.photowey"
version = "1.0.1"

repositories {
    mavenLocal()
    maven {
        isAllowInsecureProtocol = true
        setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
    }
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IU") // Target IDE Platform

    // 插件依赖
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

//    changeNotes """
//         <ul>
//            <li>1.0.1: Navigation between Java and Http request.</li>
//            <li>1.0.0: easy-http-client plugin init.</li>
//         </ul>"""
//
//    plugins = ['java','restClient','SpringBoot','SpringMvc']

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("222.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
