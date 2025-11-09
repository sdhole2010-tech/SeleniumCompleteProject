pipeline {
    agent any

    tools {
        maven 'Maven3'  // Name of Maven installation in Jenkins
        jdk 'JDK17'     // Name of JDK installation in Jenkins
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/sdhole2010-tech/SeleniumCompleteProject.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // Publish JUnit test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Generate HTML Report') {
            steps {
                sh 'mvn surefire-report:report'
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
