====================================================================
APACHE WEB LOG ANALYZER USING APACHE SPARK AND HADOOP
====================================================================

* This project is an sbt project and you need to have sbt installed on your machine.
* Steps of installation and configuration of sbt is not included here.

====================================================================
PROJECT BUILD
====================================================================
* Change your directory to the root of this project. In my case the project is on Desktop 
	
	issa@issa-fulcrum:~$cd /Desktop/project-spark

* Creating project jar file. 

	Run sbt package. This will download all dependencies and then create project jar under /target/scala-2.11/project-spark_2.11-1.0.jar
	
	issa@issa-fulcrum:~/Desktop/project-spark$ sbt package

* Running the program on spark. 

	issa@issa-fulcrum:~$ spark-submit --class VisaPredictor --master local /home/issa/Documents/project-spark/target/scala-2.11/project-spark_2.11-1.0.jar
 
	This will give you details how to run the program. Here is the message you will get when you run the jar file without providing any argument
	
	===================================================

	Usage : <balanceStmt> <holdings> <work experience> <marital status> 


	===================================================


* For example: 
	issa@issa-fulcrum:~$  spark-submit --class VisaPredictor --master local /home/issa/Documents/project-spark/target/scala-2.11/project-spark_2.11-1.0.jar 150000 10000 1 0


	

  
