pipeline {
    agent any
    stages {

        stage('Build Interface') {
            steps {
                build job: 'jogayjoga-auth', wait: true
            }
        }

        stage('Build') { 
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Image') {
            steps {
                script {
                    auth = docker.build("eriksoaress/auth:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }

        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credential') {
                        auth.push("${env.BUILD_ID}")
                        auth.push("latest")
                    }
                }
            }
        }
    }
}