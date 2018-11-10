# StockMarketAnalysis
Parallel computing analysis of NASDAQ companies

Compliation Commands:
javac -cp .:json-simple-1.1.jar:/var/tmp/parajava/pj2/pj2.jar *.java
jar cf p1.jar *.class
java pj2 debug=makespan jar=p1.jar MainSequential