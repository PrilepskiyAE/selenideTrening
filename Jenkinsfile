pipeline {
   agent {
    docker {
        image 'maven:3.8.6-openjdk-21'
        args '-v $HOME/.m2:/root/.m2'
    }
}
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
