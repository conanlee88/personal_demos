package com.daydayup

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyFirstPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.extensions.create('MyPlugin',MyExtension)
        project.task('testPlugin') << {
            println("hello my first plugin")
            println(project.MyPlugin.name)
        }
    }
}