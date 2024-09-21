pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Michael-B-Ahiale/product_mgm_sys_CI-CD.git', branch: 'main'
            }
        }

        stage('Install Dependencies') {
            steps {
                // Use Maven to install dependencies (on Windows, use 'bat')
                bat 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests with Maven (on Windows, use 'bat')
                bat 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        success {
            mail to: 'abmike268@gmail.com',
                 subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Good news, the build passed! Check the results at ${env.BUILD_URL}."
        }
        failure {
            mail to: 'abmike268@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Unfortunately, the build failed. Check the details at ${env.BUILD_URL}."
        }
    }
}
