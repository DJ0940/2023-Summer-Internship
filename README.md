# Integration Testing



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/performancelivestock/integration-testing.git
git branch -M master
git push -uf origin master
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.com/performancelivestock/integration-testing/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thank you to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README
Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name
Integration Testing

## Description
Appium codebase for (i) Performance Ranch, (ii) Blockyard Mobile (iii) Performance Beef (iv) An intern side gig for deploying an actual Twitter bot farm.

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

1. System setup for iOS
    1. Download Xcode from the app store
    2. Install Xcode command line tools, run below command in terminal
       ```zsh
       sudo xcode-select --install
       ```
    3. Install Homebrew, run below command in terminal
       ```zsh
       /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
       ```
    4. Install carthage, run below command in terminal
       ```zsh
       brew install carthage
       ```
    
2. System setup for Android
    1. Run below command in terminal to see the JDK path
       ```zsh
       /usr/libexec/java_home
       ```
       If you haven't downloaded JDK, download [JDK](https://www.oracle.com/java/technologies/downloads/#jdk20-linux)
    2. Download [Android Studio](https://developer.android.com/studio)
    3. Open up Android Studio -> customize -> all settings -> Android SDK under Appearance & Behavior/System Settings -> SDK Tools tab
       Check Android SDK Build-Tools 34, Android SDK Command-line Tools, Android Emulator, Android SDK Platform-Tools, Intel x86 Emulator Accelerator (HAXM installer) is installed

3. Install Appium
    1. Need to download 2.0.0-beta. version
      ```zsh
      npm install -g appium@next
      ```
      Add sudo infront the command if asks for permission. You can check the version by typing `appium -v` in terminal
    2. Download appium-doctor
      ```zsh
      'npm install -g appium-doctor' then run command 'appium-doctor'
      ```
    3. If either ANDROID_HOME or JAVA_HOME shows X, need to set the path
        - Setting ANDROID_HOME
            1. If your terminal is in zsh, run `vim ~/.zprofile`. Then add below commands inside the .zprofile
            ```zsh
            export ANDROID_HOME=~/Library/Android/sdk
            export PATH=$PATH:$ANDROID_HOME/platform-tools
            export PATH=$PATH:$ANDROID_HOME/tools
            export PATH=$PATH:$ANDROID_HOME/tools/bin
            export PATH=$PATH:$ANDROID_HOME/emulator
            ```
            
        - Setting JAVA_HOME
            1. Get JDK path from Step 2.1
            2. Add the command inside .zprofile
               ```zsh
               export JAVA_HOME=YOUR-JDK-PATH
               ```

        - Close the terminal and re-open it. Run `appium-doctor` to see X mark changed to O

    4. Install appium drivers
      ```zsh
      appium driver install uiautomator2
      appium driver install xcuitest
      ```

4. Run Appium
   ```zsh
   appium --base-path /wd/hub
   ```

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.

## Suggestions:
Use this document's existing framework to build out a setup guide for someone new to the project that would want to deploy integration testing for thier project.
