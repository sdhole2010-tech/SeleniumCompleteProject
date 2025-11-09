pipeline {
    agent any

    tools {
        jdk 'JDK17'       // Your Jenkins JDK name
        maven 'Maven3'    // Your Jenkins Maven name
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sdhole2010-tech/SeleniumCompleteProject.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate HTML Report') {
            steps {
                bat 'mvn surefire-report:report'
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'surefire-report.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }

    post {
        success {
            echo 'Build & Tests Successful!'
        }
        failure {
            echo 'Build or Tests Failed!'
        }
    }
}
