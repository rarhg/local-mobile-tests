pipeline {
    agent any

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
                sh 'gradle clean'
            }
        }

        stage('Параллельное тестирование (Кроссплатформа)') {
            stage('Параллельное тестирование (Кроссплатформа в Облаке)') {
                parallel {
                    stage('Тесты: Облачный Android (BrowserStack)') {
                        steps {
                            echo 'Запуск автотестов на Android в облаке...'
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
                            echo 'Загрузка iOS приложения в облако BrowserStack и запуск...'
                            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                                withCredentials([
                                    string(credentialsId: 'browserstack_username', variable: 'BS_USER'),
                                    string(credentialsId: 'browserstack_access_key', variable: 'BS_KEY')
                                ]) {
                                    script {
                                        echo 'Загрузка iOS APK/IPA-файла в хранилище BrowserStack...'
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
    }

    post {
        always {
            echo 'Генерация единого отчета Allure...'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
        }
    }
}
