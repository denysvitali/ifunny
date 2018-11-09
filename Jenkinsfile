pipeline {
  env {
    PROJECT_NAME
  }
  agent any
  stages {
    stage("Slack Notification"){
      steps {
        slackSend color: '#2c3e50', message: "**${PROJECT_NAME}**\nStarted build <${BUILD_URL}|#${BUILD_NUMBER}> for ${JOB_NAME} (<https://git.ded1.denv.it/husky/ifunny/commit/${GIT_COMMIT}|${GIT_COMMIT}>) on branch $GIT_BRANCH."
      }
    }

    stage("Cleanup"){
      steps {
        sh "git clean -fdx"
      }
    }

    stage("Build"){
      steps {
        script {
          docker.image('maven:3-jdk-11').inside() {
            sh "mvn compile"
          }
        }
      }
    }
    stage("Test"){
      steps {
        script {
          docker.image('maven:3-jdk-11').inside() {
            sh "mvn test -Dmaven.compile.skip=true"
            junit 'target/surefire-reports/**/*.xml'
          }
        }
      }
    }
  }
  post {
    failure {
        slackSend color: 'danger', message: "**${PROJECT_NAME}**\nBuild <${BUILD_URL}|#${BUILD_NUMBER}> *failed*! Branch $GIT_BRANCH, commit: (<https://git.ded1.denv.it/husky/ifunny/commit/${GIT_COMMIT}|${GIT_COMMIT}>). :respects:\n\n*Commit Log*:\n${COMMIT_LOG}"
    }
    success {
        slackSend color: 'good', message: "**${PROJECT_NAME}**\nBuild <${BUILD_URL}|#${BUILD_NUMBER}> *successful*! Branch $GIT_BRANCH, commit: (<https://git.ded1.denv.it/husky/ifunny/commit/${GIT_COMMIT}|${GIT_COMMIT}>). :tada: :blobdance: :clappa:\n\n*Commit Log*:\n${COMMIT_LOG}"
    }
    always {
        script {
          COMMIT_LOG = sh(script:"git log --oneline --pretty=format:'%h - %s (%an)' ${GIT_PREVIOUS_COMMIT}..HEAD", returnStdout: true)
        }
        cleanWs()
    }
  }
}
