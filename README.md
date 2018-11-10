# StockMarketAnalysis
Parallel computing analysis of NASDAQ companies

Compliation Commands:
JAMES:
export CLASSPATH=.:/home/stu10/s5/jef1771/Courses/CS654/stocks/StockMarketAnalysis/Stocks_Java/src/json-simple-1.1.jar:/var/tmp/parajava/pj2/pj2.jar
LI:
export CLASSPATH=.:/home/stu2/s19/zxl3946/Desktop/StockMarketAnalysis/Stocks_Java/src/json-simple-1.1.jar:/var/tmp/parajava/pj2/pj2.jar
export PATH=/usr/local/dcs/versions/jdk1.7.0_51/bin:$PATH
javac -cp $CLASSPATH *.java
jar cf p1.jar *.class
java pj2 debug=makespan jar=p1.jar MainSequential