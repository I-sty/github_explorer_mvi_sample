package com.i_sz_app.githubexplorer.domain.model

enum class RepositorySort(val value: String) {
    Stars("stars"),
    Forks("forks"),
    HelpWantedIssues("help-wanted-issues"),
    Updated("updated"),
    BestMatch(""),
}
