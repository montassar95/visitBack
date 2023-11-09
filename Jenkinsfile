pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/montassar95/visitBack.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}