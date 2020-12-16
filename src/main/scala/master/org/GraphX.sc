import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.lib._

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","true")

val sc = new SparkContext(conf)
val neo = Neo4j(sc)

// load graph via Cypher query
val graphQuery = "MATCH (n:Person)-[r:KNOWS]->(m:Person) RETURN id(n) as source, id(m) as target, type(r) as value SKIP {_skip} LIMIT {_limit}"
val graph: Graph[Long, String] = neo.rels(graphQuery).partitions(7).batch(200).loadGraph

graph.vertices.count
//    => 100
graph.edges.count
//    => 1000

// load graph via pattern
val graph = neo.pattern(("Person","id"),("KNOWS","since"),("Person","id")).partitions(7).batch(200).loadGraph[Long,Long]

val graph2 = PageRank.run(graph, 5)
//    => graph2: org.apache.spark.graphx.Graph[Double,Double] =

graph2.vertices.sort(_._2).take(3)
//    => res46: Array[(org.apache.spark.graphx.VertexId, Long)]
//    => Array((236746,100), (236745,99), (236744,98))

// uses pattern from above to save the data, merge parameter is false by default, only update existing nodes
neo.saveGraph(graph, "rank")
// uses pattern from parameter to save the data, merge = true also create new nodes and relationships
neo.saveGraph(graph, "rank",Pattern(("Person","id"),("FRIEND","years"),("Person","id")), merge = true)

