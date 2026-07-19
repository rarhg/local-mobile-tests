pipeline {
    agent any

    tools {
        gradle '8'
    }

    parameters {
        string(name: 'THREADS', defaultValue: '1', description: 'Количество потоков для каждого стенда')
    }

    stages {
        stage('Подготовка проекта') {
            steps {
                checkout scm
                echo 'Выдача прав на запуск Gradle в Linux окружении...'
                sh 'chmod +x gradlew'
                echo 'Очистка предыдущей сборки...'
                sh './gradlew clean'
            }
        }

        stage('Параллельное тестирование (Кроссплатформа в Облаке)') {
            parallel {
                stage('Тесты: Облачный Android (BrowserStack)') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withCredentials([
                                string(credentialsId: 'browserstack_username', variable: 'BS_USER'),
                                string(credentialsId: 'browserstack_access_key', variable: 'BS_KEY')
                            ]) {
                                sh "./gradlew android -DdeviceHost=browserstack -Pweb_threads=${params.THREADS}"
                            }
                        }
                    }
                }

                stage('Тесты: Облачный iOS (BrowserStack)') {
                    steps {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withCredentials([
                                string(credentialsId: 'browserstack_username', variable: 'BS_USER'),
                                string(credentialsId: 'browserstack_access_key', variable: 'BS_KEY')
                            ]) {
                                script {
                                    sh """
                                        curl -u "${BS_USER}:${BS_KEY}" \
                                        -X POST "https://browserstack.com" \
                                        -F "file=@src/test/resources/apps/wikipedia.ipa"
                                    """
                                }
                                sh "./gradlew ios -DdeviceHost=browserstack -Pweb_threads=${params.THREADS}"
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
    }
}
