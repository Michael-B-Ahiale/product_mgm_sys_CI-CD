pipeline {
    agent any

    environment {
        SMTP_CREDS = credentials('jenkins-email-auth')
    }

    tools {
        maven 'Maven 3.9.9'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Michael-B-Ahiale/product_mgm_sys_CI-CD.git', branch: 'main'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Generate Reports') {
            steps {
                bat 'mvn surefire-report:report'
                bat 'mvn site'

                // Generate enhanced XML report for API testing
                script {
                    def testResults = junit '**/target/surefire-reports/*.xml'

                    def xmlReport = """<?xml version="1.0" encoding="UTF-8"?>
                    <api-test-report>
                        <build>
                            <job>${env.JOB_NAME}</job>
                            <number>${env.BUILD_NUMBER}</number>
                            <result>${currentBuild.currentResult}</result>
                            <timestamp>${new Date().format("yyyy-MM-dd HH:mm:ss")}</timestamp>
                        </build>
                        <test-summary>
                            <total>${testResults.totalCount}</total>
                            <passed>${testResults.passCount}</passed>
                            <failed>${testResults.failCount}</failed>
                            <skipped>${testResults.skipCount}</skipped>
                        </test-summary>
                        <test-cases>
                    """

                    testResults.each { test ->
                        xmlReport += """
                            <test-case>
                                <name>${test.name}</name>
                                <class-name>${test.className}</class-name>
                                <duration>${test.duration}</duration>
                                <status>${test.status}</status>
                                ${test.errorDetails ? "<error-details>${test.errorDetails}</error-details>" : ""}
                                ${test.errorStackTrace ? "<error-stack-trace>${test.errorStackTrace}</error-stack-trace>" : ""}
                            </test-case>
                        """
                    }

                    xmlReport += """
                        </test-cases>
                    </api-test-report>
                    """

                    writeFile file: 'api-test-report.xml', text: xmlReport
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'

            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: 'target/site',
                reportFiles: 'surefire-report.html',
                reportName: 'HTML Test Report'
            ])
        }
        success {
            echo 'Build successful'
            script {
                emailext (
                    subject: "SUCCESS: API Tests for '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                    body: """<table style="border: 2px solid green; border-collapse: collapse; width: 100%;">
                        <tr style="background-color: #4CAF50; color: white;">
                            <th style="padding: 10px; text-align: left;">Status</th>
                            <th style="padding: 10px; text-align: left;">Job</th>
                            <th style="padding: 10px; text-align: left;">Build</th>
                        </tr>
                        <tr>
                            <td style="padding: 10px; color: green; font-weight: bold;">SUCCESS</td>
                            <td style="padding: 10px;">${env.JOB_NAME}</td>
                            <td style="padding: 10px;">${env.BUILD_NUMBER}</td>
                        </tr>
                    </table>
                    <p>Check <a href='${env.BUILD_URL}'>console output</a></p>
                    <p>View <a href='${env.BUILD_URL}HTML_20Test_20Report/'>HTML Test Report</a></p>
                    <p>A detailed XML report of the API tests is attached to this email.</p>""",
                    to: 'abmike268@gmail.com',
                    from: 'abmike268@gmail.com',
                    replyTo: 'abmike268@gmail.com',
                    mimeType: 'text/html',
                    attachmentsPattern: '**/api-test-report.xml, **/target/site/surefire-report.html',
                    compressLog: true,
                    auth: true,
                    smtpServer: 'smtp.gmail.com',
                    smtpPort: '465',
                    useSsl: true,
                    username: '$SMTP_CREDS_USR',
                    password: '$SMTP_CREDS_PSW'
                )
            }
        }
        failure {
            echo 'Build failed'
            script {
                emailext (
                    subject: "FAILED: API Tests for '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                    body: """<table style="border: 2px solid red; border-collapse: collapse; width: 100%;">
                        <tr style="background-color: #F44336; color: white;">
                            <th style="padding: 10px; text-align: left;">Status</th>
                            <th style="padding: 10px; text-align: left;">Job</th>
                            <th style="padding: 10px; text-align: left;">Build</th>
                        </tr>
                        <tr>
                            <td style="padding: 10px; color: red; font-weight: bold;">FAILED</td>
                            <td style="padding: 10px;">${env.JOB_NAME}</td>
                            <td style="padding: 10px;">${env.BUILD_NUMBER}</td>
                        </tr>
                    </table>
                    <p>Check <a href='${env.BUILD_URL}'>console output</a></p>
                    <p>View <a href='${env.BUILD_URL}HTML_20Test_20Report/'>HTML Test Report</a></p>
                    <p>A detailed XML report of the API tests is attached to this email.</p>""",
                    to: 'abmike268@gmail.com',
                    from: 'abmike268@gmail.com',
                    replyTo: 'abmike268@gmail.com',
                    mimeType: 'text/html',
                    attachmentsPattern: '**/api-test-report.xml, **/target/site/surefire-report.html',
                    compressLog: true,
                    auth: true,
                    smtpServer: 'smtp.gmail.com',
                    smtpPort: '465',
                    useSsl: true,
                    username: '$SMTP_CREDS_USR',
                    password: '$SMTP_CREDS_PSW'
                )
            }
        }
    }
}