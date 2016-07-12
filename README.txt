====================================================================
APACHE WEB LOG ANALYZER USING APACHE SPARK AND HADOOP
AND VISUALIZATION USING R
====================================================================

* This project is an sbt project and you need to have sbt installed on your machine.
* Steps of installation and configuration of sbt is not included here.

====================================================================
PROJECT BUILD
====================================================================
* Change your directory to the root of this project. In my case the project is on Desktop 
	
	issa@issa-fulcrum:~$cd /Desktop/spark-log-analysis

* Creating project jar file. 

	Run sbt package. This will download all dependencies and then create project jar under /target/scala-2.11/spark-log-analysis_2.11-1.0.jar
	
	issa@issa-fulcrum:~/Desktop/spark-log-analysis$ sbt package

* Running the program on spark. 

	issa@issa-fulcrum:~$ spark-submit --class LogAnalyzer --master local /home/issa/Desktop/spark-log-analysis_2.11-1.0.jar


* For example: 
	issa@issa-fulcrum:~$



# apache access log file used from http://www.almhuette-raith.at/apache-log/access.log


#Running r script. Go to the directory where you extracted the project and running the following on r console. Change directory to your directory
 source('~/Desktop/spark-log-analysis/src/main/resources/visualizer.R')

	

  
