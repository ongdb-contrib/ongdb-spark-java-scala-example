## 安装
- 下载组件
```
https://github.com/ongdb-contrib/neo4j-spark-connector/releases/tag/2.4.1-M1
```
- 下载组件放在spark安装目录的jars文件夹
```
E:\software\ongdb-spark\spark-2.4.0-bin-hadoop2.7\jars
```

## 基础组件依赖信息
- 版本
```
Spark 2.4.0  http://archive.apache.org/dist/spark/spark-2.4.0/
ONgDB 3.5.x
Driver 1.7.5
Scala 2.11
JDK 1.8
hadoop-2.7.7
https://mirrors.tuna.tsinghua.edu.cn/apache/hadoop/common/
neo4j-spark-connector-full-2.4.1-M1 https://github.com/neo4j-contrib/neo4j-spark-connector
```
- 下载包
```
hadoop-2.7.7
spark-2.4.0-bin-hadoop2.7
winutils
neo4j-spark-connector-full-2.4.1-M1 【把jar包放到spark/jars文件夹里】
scala-2.11.12
```

## 创建测试数据
```
UNWIND range(1,100) as id
CREATE (p:Person {id:id}) WITH collect(p) as people
UNWIND people as p1
UNWIND range(1,10) as friend
WITH p1, people[(p1.id + friend) % size(people)] as p2
CREATE (p1)-[:KNOWS {years: abs(p2.id - p2.id)}]->(p2)
```
```
FOREACH (x in range(1,1000000) | CREATE (:Person {name:"name"+x, age: x%100}));
```
```
UNWIND range(1,1000000) as x
MATCH (n),(m) WHERE id(n) = x AND id(m)=toInt(rand()*1000000)
CREATE (n)-[:KNOWS]->(m);
```

## 备注
>下载依赖包如果出现问题请检查下面网址是否可以正常下载SPARK相关的JAR包
```
http://dl.bintray.com/spark-packages/maven
```
