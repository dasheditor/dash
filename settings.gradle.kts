include("app")
include("compiler")

rootProject.name = "dash"

for (project in rootProject.children) {
    project.projectDir = file("subprojects/${project.name}")
}