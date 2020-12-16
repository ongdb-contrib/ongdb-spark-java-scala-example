// 调用成功
import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._
import org.apache.spark.rdd.RDD
//import org.apache.spark.rdd.Row
import org.apache.spark.sql.{DataFrame, Row, SQLContext}

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","true")

val sc = new SparkContext(conf)

val neo4j = new Neo4j(sc)

val new_neo4j: Neo4j = neo4j.cypher("MATCH (n:Person) RETURN ID(n)")
val dataFrame: DataFrame = new_neo4j.loadDataFrame

val new_neo4j: Neo4j = neo4j.cypher("CALL algo.pageRank.stream('Page', 'LINKS', {iterations:20, dampingFactor:0.85}) YIELD nodeId, score RETURN algo.asNode(nodeId).name AS page,score ORDER BY score DESC")
val frame: DataFrame = new_neo4j.loadDataFrame

dataFrame.show()

//sc.close()




