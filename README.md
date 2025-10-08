Gradle sample project
==================

## Sample multi-module gradle project integration with GitLab and Codacy

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/092dd01a68f54053ad41db84abb12fe5)](https://www.codacy.com/gl/ubb-mle5109/sample-project/dashboard?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=ubb-mle5109/sample-project&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/092dd01a68f54053ad41db84abb12fe5)](https://www.codacy.com/gl/ubb-mle5109/sample-project/dashboard?utm_source=gitlab.com&utm_medium=referral&utm_content=ubb-mle5109/sample-project&utm_campaign=Badge_Coverage)


This repository is intended as an example of how a multi-module Gradle project can be setup and integrated with:
* **[Codacy](https://www.codacy.com "Codacy")** - static analysis front-end that uses tools for a number of languages. When integrated with your GitHub repo, it analyses your master branch and any other you select in the settings and gives you a summary of possible issues with your code. Not only that; whenever a new pull request is opened, it checks whether it fixes any of those issue (good) or it adds new ones (bad). This allows you to try and set a trend towards cleanliness, or at least to avoid getting further from it.
* **[GitLab](https://gitlab.com/)**

#### Codacy integration

After logging in with your GitLab account and giving the needed permissions, go ahead and add a project from the available projects. Codacy will analyse and review the project and then provide a dashboard with all sort of statistics.
![Codacy Login](/images/codacy_login.png?raw=true "Codacy Login")
![Codacy Add Organization](/images/codacy_add_organization.png?raw=true "Codacy Add Organization")
![Codacy Cloning](/images/codacy_cloning.png?raw=true "Codacy Cloning one you added a project")

Besides static code analysis, Codacy supports code coverage reports as well which will be provided by GitLab-CI after building and running the tests. For that a *Project API* integration is needed so that Codacy accepts coverage reports sent by CI server.
For this add a new integration (Project > Settings > Integrations : Add Integration)
![Codacy Project API](/images/codacy_api.png?raw=true "Codacy Project API")

Add the generated Project API as a variable in GiLab's project CI/CD settings (GitLab (project) > Settings > CI/CD : Variables)
![GitLab variable](/images/gitlab_codacy_token.png?raw=true "Gitlab Codacy project token")
- check gitlab-ci.yml for codacy integration for coverage

Codacy provide status Badges that can be integrated with GitLab:
![Codacy Badge](/images/codacy_badge.png?raw=true "Codacy Badge")

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/de4f266e479a4789b9081f047026c630)](https://app.codacy.com/gl/alextm0/ppoc-laboratory/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
