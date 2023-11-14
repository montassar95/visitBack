node {
    def WORKSPACE = "/var/lib/jenkins/workspace/visit-back-demo-deploy"
    def dockerImageTag = "visitback${env.BUILD_NUMBER}"

    try {
        notifyBuild('STARTED')

        stage('Clone Repo') {
            // for display purposes
            // Get some code from a GitHub repository
            git url: 'https://github.com/montassar95/visitback.git',
                credentialsId: 'visitback-user',
                branch: 'master'
        }
        
        

      stage('minikube env') {
            sh "eval $(minikube docker-env)"
      }


      stage('Build docker') {
             dockerImage = docker.build("visitback:${env.BUILD_NUMBER}")
      }

      stage('Deploy docker'){
              echo "Docker Image Tag Name: ${dockerImageTag}"
              sh "docker stop visitback || true && docker rm visitback || true"
              sh "docker run --name visitback -d -p 8081:8081 visitback:${env.BUILD_NUMBER}"
      }
      
       stage('Deploy k8s'){
              echo " lancement k8s "
              sh "kubectl apply -f k8s-deployment.yaml"
              sh "kubectl apply -f service.yaml"
              
              
      }
      
    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
        notifyBuild(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESSFUL'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def now = new Date()

  // message
  def subject = "${buildStatus}, Job: ${env.JOB_NAME} FRONTEND - Deployment Sequence: [${env.BUILD_NUMBER}] "
  def summary = "${subject} - Check On: (${env.BUILD_URL}) - Time: ${now}"
  def subject_email = "Spring boot Deployment"
  def details = """<p>${buildStatus} JOB </p>
    <p>Job: ${env.JOB_NAME} - Deployment Sequence: [${env.BUILD_NUMBER}] - Time: ${now}</p>
    <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME}</a>"</p>"""
}
