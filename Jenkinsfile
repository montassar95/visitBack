node {
    def WORKSPACE = "/var/lib/jenkins/workspace/visit-back-demo-deploy"
    def dockerImageTag = "visitback"
    def dockerHubUsername = "montassar95"
    def dockerHubPasswordX = "123456docker"

    try {
        notifyBuild('STARTED')

        stage('Clone Repo') {
            // Obtenir du code depuis un référentiel GitHub
            git url: 'https://github.com/montassar95/visitback.git',
                credentialsId: 'visitback-user',
                branch: 'master'
        }

        stage('Build Docker') {
          echo "Nom du tag de l'image Docker au Build Docker : ${dockerImageTag}"
            // Construire l'image Docker
           // without Tag :  sh "docker build -t ${dockerHubUsername}/${dockerImageTag} ."
            //dockerImage = docker.build("${dockerImageTag}")
            sh "docker build -t ${dockerImageTag} ."
            sh "docker images"
            
            
        }

        stage('Push vers Docker Hub') {
        
          echo "Nom du tag de l'image Docker au Push : ${dockerImageTag}"
            // Connecter Docker à Docker Hub
            // withCredentials([string(credentialsId: 'montassar-docker-hub-credentials-id', variable: 'dockerhubpwd')]) {
              //     sh 'docker login -u ${dockerHubUsername} -p ${dockerhubpwd}'

             //}
          //sh "docker login -u ${dockerHubUsername} -p ${dockerHubPassword}"
withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
   sh 'docker login -u montassar95 -p ${dockerhubpwd}'
}
			 echo "docker tag ${dockerImageTag} ${dockerHubUsername}/${dockerImageTag} "
			 sh "docker tag ${dockerImageTag} ${dockerHubUsername}/${dockerImageTag} "
             // Pousser l'image vers Docker Hub
            sh "docker push ${dockerHubUsername}/${dockerImageTag}"
           
        }

        //stage('Déployer Docker') {
           // echo "Nom du tag de l'image Docker au Déployement : ${dockerImageTag}"
          //  sh "docker stop visitback || true && docker rm visitback || true"
          //  sh "docker run --name visitback -d -p 8081:8081 ${dockerHubUsername}/${dockerImageTag}"
        //}


		
		stage('Déploiement Kubernetes') {
  
  			echo "lancement du deploiment kubernetes"

            kubernetesDeploy (configs: 'k8s-deployment.yaml', kubeconfigId: 'k8sconfigpwd')



            
           // sh 'kubectl config use-context minikube'  // Assurez-vous que le contexte est correct
           //sh 'kubectl --kubeconfig=/home/montassar/.kube/config apply -f k8s-deployment.yaml'
           //sh 'kubectl --kubeconfig=/home/montassar/.kube/config apply -f service.yaml'
            // Déployer l'application sur Kubernetes
            //sh 'kubectl apply -f k8s-deployment.yaml'
            //sh 'kubectl apply -f service.yaml'
    
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

    // Message
    def subject = "${buildStatus}, Job: ${env.JOB_NAME} FRONTEND - Sequence de déploiement : [${env.BUILD_NUMBER}] "
    def summary = "${subject} - Vérifiez sur : (${env.BUILD_URL}) - Heure : ${now}"
    def subject_email = "Déploiement Spring Boot"
    def details = """<p>${buildStatus} JOB </p>
    <p>Job: ${env.JOB_NAME} - Sequence de déploiement : [${env.BUILD_NUMBER}] - Heure : ${now}</p>
    <p>Vérifiez la sortie de la console à "<a href="${env.BUILD_URL}">${env.JOB_NAME}</a>"</p>"""
}
