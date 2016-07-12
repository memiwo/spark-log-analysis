  
  library(ggplot2)
  library(plotly)
  library(dplyr)
  #set sparkr home
  Sys.setenv(SPARK_HOME = "/opt/spark")
  .libPaths(c(file.path(Sys.getenv("SPARK_HOME"), "R","lib"), .libPaths()))
  #load SparkR library
  library(SparkR)
  
  #Create a spark context and a SQL context
  sc <- sparkR.init(sparkPackages="com.databricks:spark-csv_2.10:1.3.0")
  
  sqlContext <- sparkRSQL.init(sc)
  
  accessByHour <- read.df(sqlContext, "hdfs://localhost:9001/bigdata/analytics/output/byHour/part-00000","com.databricks.spark.csv", header="true")
  
  #convert sparkr data frame in to r data frame
  accessByHour <- collect(select(accessByHour, "hour", "count"))
  accessByHour$hour <- factor(as.integer(accessByHour$hour))
  accessByHour$count <- factor(as.integer(accessByHour$count))
  
  byHourPlot <- ggplot(NULL, aes(x = accessByHour$hour, y=accessByHour$count)) + geom_bar(stat = "identity") + xlab("Hour") + ylab("Hits")
  
  
  
  ggplotly(byHourPlot)
  
  
  accessByIp <- read.df(sqlContext, "hdfs://localhost:9001/bigdata/analytics/output/byIpAddress/part-00000","com.databricks.spark.csv", header="true")
  accessByIp <- collect(select(accessByIp, "ipAddress", "count"))
  accessByIp$count <- as.integer(accessByIp$count)
  accessByIp2 <- sqldf("select * from accessByIp limit 10")
  View(accessByIp2)