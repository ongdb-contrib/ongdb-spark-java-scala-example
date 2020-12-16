import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","true")

val sc = new SparkContext(conf)

val neo = Neo4j(sc)

val rdd = neo.cypher("MATCH (n:Person) RETURN id(n) as id ").loadRowRdd
rdd.count

// inferred schema
rdd.first.schema.fieldNames
//   => ["id"]
rdd.first.schema("id")
//   => StructField(id,LongType,true)

neo.cypher("MATCH (n:Person) RETURN id(n)").loadRdd[Long].mean
//   => res30: Double = 236696.5

neo.cypher("MATCH (n:Person) WHERE n.id <= {maxId} RETURN n.id").param("maxId", 10).loadRowRdd.count
//   => res34: Long = 10

// provide partitions and batch-size
//neo.nodes("MATCH (n:Person) RETURN id(n) SKIP {_skip} LIMIT {_limit}").partitions(4).batch(25).loadRowRdd.count
//   => 100 == 4 * 25

// load via pattern
neo.pattern("Person",Seq("KNOWS"),"Person").rows(80).batch(21).loadNodeRdds.count
//   => 80 = b/c 80 rows given

// load relationships via pattern
neo.pattern("Person",Seq("KNOWS"),"Person").partitions(12).batch(100).loadRelRdd.count
//   => 1000

