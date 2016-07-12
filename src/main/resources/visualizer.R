  
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
  
  byHourPlot <- ggplot(NULL, aes(x = accessByHour$hour, y=accessByHour$count)) + geom_bar(colour="#DD8888", fill="#DD8888",stat = "identity") + xlab("Hour") + ylab("Hits")
  
  ggplotly(byHourPlot)
  
  
  accessByIp <- read.df(sqlContext, "hdfs://localhost:9001/bigdata/analytics/output/byIpAddress/part-00000","com.databricks.spark.csv", header="true")
  accessByIp <- take(accessByIp, 10)
 # accessByIp <- collect(select(accessByIp, "ipAddress", "count"))
  accessByIp$count <- factor(as.integer(accessByIp$count))
  byIpPlot <- ggplot(NULL, aes(x = accessByIp$ipAddress, y = accessByIp$count, group = 1)) + geom_line(color = accessByIp$count) + geom_point() + xlab("Ip Address") + ylab("Hits")
  ggplotly(byIpPlot)
  
  accessByHttpStatus <- read.df(sqlContext, "hdfs://localhost:9001/bigdata/analytics/output/byHttpStatus/part-00000","com.databricks.spark.csv", header="true")
  accessByHttpStatus <- collect(select(accessByHttpStatus, "httpStatus", "count"))
  accessByHttpStatus$count <- factor(as.integer(accessByHttpStatus$count))
  plot_ly(accessByHttpStatus, labels = accessByHttpStatus$httpStatus, values = accessByHttpStatus$count, type = "pie")
  sparkR.stop()