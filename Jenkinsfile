pipeline {
  agent any
  stages {
    stage('Paso 1') {
      parallel {
        stage('Paso 1') {
          agent any
          steps {
            echo 'Prueba 1'
            mail(subject: 'Prueba', body: 'Prueba 2')
            timestamps()
          }
        }
        stage('Otro') {
          steps {
            echo 'Otro'
          }
        }
      }
    }
  }
}