pipeline {
    agent any

    parameters {
        string(name: 'THREADS', defaultValue: '1', description: 'Количество потоков для каждого стенда')
    }

    stages {
        stage('Подготовка проекта') {
            steps {
                echo 'Очистка предыдущей сборки...'
                sh './gradlew clean'
            }
        }

        stage('Параллельное тестирование (Кроссплатформа)') {
            parallel {
                stage('Тесты: Локальный Android') {
                    steps {
                        echo 'Запуск автотестов на локальном эмуляторе/девайсе Android...'
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            sh "./gradlew android -DdeviceHost=emulation -Pweb_threads=${params.THREADS}"
                        }
                    }
                }

                stage('Тесты: Облачный iOS (BrowserStack)') {
                    steps {
                        echo 'Загрузка iOS приложения в облако BrowserStack и запуск...'
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                            withCredentials([
                                string(credentialsId: 'browserstack_username', variable: 'BS_USER'),
                                string(credentialsId: 'browserstack_access_key', variable: 'BS_KEY')
                            ]) {
                                script {
                                    echo 'Загрузка iOS APK/IPA-файла в хранилище BrowserStack...'
                                    def response = sh(
                                        script: """
                                            curl -u "${BS_USER}:${BS_KEY}" \
                                            -X POST "https://browserstack.com" \
                                            -F "file=@src/test/resources/apps/wikipedia.ipa"
                                        """,
                                        returnStdout: true
                                    ).trim()
                                    echo "Ответ от BrowserStack: ${response}"
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
            echo 'Генерация единого отчета Allure...'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
    }
}
