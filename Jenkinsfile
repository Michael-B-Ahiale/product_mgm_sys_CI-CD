pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git url: 'https://github.com/Michael-B-Ahiale/product_mgm_sys_CI-CD.git', branch: 'main'
            }
        }

        stage('Install Dependencies') {
            steps {
                // Use Maven to install dependencies
                sh 'mvn clean install'
            }
        }

        stage('Run Tests') {
            steps {
                // Run tests with Maven
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results even if the build fails
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Report Results') {
            steps {
                // Publish JUnit or HTML test reports (depending on your test setup)
                junit '**/target/surefire-reports/*.xml'
                // Optionally, publish HTML reports
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'index.html',
                    reportName: 'HTML Report'
                ])
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
