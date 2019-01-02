node {
	def mvn_version = 'M3'
	def server =Artifactory.server 'artf1'
   
	try{
        stage('Checkout'){
            git 'https://github.com/adijain3008/mvcDemo.git'
        }
        
        stage('SonarQube analysis') {
            withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                withSonarQubeEnv('sonar_env') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        
        stage("SonarQube Quality Gate") { 
            timeout(time: 1, unit: 'HOURS') { 
                def qg = waitForQualityGate() 
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }
        }
        
        stage('Build') {
            withEnv( ["PATH+MAVEN=${tool mvn_version}/bin"] ) {
                sh "mvn clean install"
            } 
        }
        
        stage('Artifact upload') {
           	def uploadSpec = """{
                "files": [
                    {
                        "pattern": "/var/lib/jenkins/workspace/email-webhook/target/*.war",
                        "target": "Jenkins-Snapshot"
                    }
                ]
            }"""
            server.upload(uploadSpec)
        }
        
        stage('downloading artifact') {
            def downloadSpec="""{
				"files":[
					{
						"pattern":"Jenkins-Snapshot/mvcDemo.war",
						"target":"/var/lib/jenkins/warFiles/"
					}
				]
            }"""
            server.download(downloadSpec)
        }
        
        stage ('Final deploy') {
            sh 'scp /var/lib/jenkins/warFiles/mvcDemo.war minduser@104.211.163.253:/opt/tomcat/webapps/'
        }
        
	}catch(err){
		currentBuild.result = 'FAILURE'
	}
   
    def user = "project.learning12"
    
    stage('JIRA') {
        withEnv(["JIRA_SITE=jira_jenkins"]) {
		if(currentBuild.result == 'FAILURE') {
			def testIssue = [fields: [project: [id: '10001'],
					  summary: 'New Bug.',
					  description: 'New Bug Created while building project in Jenkins.',
					  issuetype: [name: 'Bug']]]
			response = jiraNewIssue issue: testIssue
			jiraAssignIssue idOrKey: response.data.key, userName: user
			jiraComment body: 'Solve this issue. Build ID: ' + env.BUILD_ID, issueKey: response.data.key
			echo response.successful.toString()
			echo response.data.toString()
		}
        }
    }
}
