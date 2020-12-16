// 调用成功
import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._
//import org.apache.spark.graphx.{Edge, Graph, VertexId}

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","false")

val sc = new SparkContext(conf)

val neo4j = new Neo4j(sc)

val graph = Neo4jGraph.loadGraph(sc, "Person", Seq.empty, "Entity")

println(graph.vertices.count)
println(graph.edges.count)

//sc.close()

