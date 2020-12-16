import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","true")

val sc = new SparkContext(conf)

val neo = Neo4j(sc)

// load via Cypher query
neo.cypher("MATCH (n:Person) RETURN id(n) as id SKIP {_skip} LIMIT {_limit}").partitions(4).batch(25).loadDataFrame.count
//   => res36: Long = 100

val df = neo.pattern("Person",Seq("KNOWS"),"Person").partitions(12).batch(100).loadDataFrame
//   => org.apache.spark.sql.DataFrame = [id: bigint]

// TODO loadRelDataFrame

