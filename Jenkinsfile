pipeline {
   agent any
    stages {
       stage('Verify Java Setup') {
    steps {
        sh 'echo "JAVA_HOME: $JAVA_HOME"'
        sh 'echo "PATH: $PATH"'
        sh 'java -version'
        sh 'javac -version'
    }
}
        stage('Hello') {
            steps {
                echo 'Hello, Jenkins is working!'
                sh 'pwd'
                sh 'ls -la'
            }
           
        }
    }
}
