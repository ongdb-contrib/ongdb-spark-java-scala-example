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
val new_neo4j: Neo4j = neo4j.cypher("MATCH (n:Person) RETURN n.NAME AS name")
val rdd: RDD[Row] = new_neo4j.loadRowRdd

rdd.count()
rdd.collect()
rdd.take(10)



