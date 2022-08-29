package io.github.dasheditor.project

import java.io.File

class DashProject(private val projectFile: File) {

    fun getProjectDir(): File {
        return projectFile.parentFile
    }
}