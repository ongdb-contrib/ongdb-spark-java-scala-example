import org.apache.spark.{SparkConf, SparkContext}
import org.neo4j.spark._

val conf : SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
conf.set("spark.neo4j.bolt.url","bolt://10.20.0.157:7787")
conf.set("spark.neo4j.bolt.user","ongdb")
conf.set("spark.neo4j.bolt.password","ongdb%dev")
conf.set("spark.driver.allowMultipleContexts","true")

val sc = new SparkContext(conf)
val neo = Neo4j(sc)

val graphFrame = neo.pattern(("Person","id"),("KNOWS",null), ("Person","id")).partitions(3).rows(1000).loadGraphFrame

graphFrame.vertices.count
//     => 100
graphFrame.edges.count
//     => 1000

val pageRankFrame = graphFrame.pageRank.maxIter(5).run()
val ranked = pageRankFrame.vertices
ranked.printSchema()

val top3 = ranked.orderBy(ranked.col("pagerank").desc).take(3)
//     => top3: Array[org.apache.spark.sql.Row]
//     => Array([236716,70,0.62285...], [236653,7,0.62285...], [236658,12,0.62285])


// example loading a graph frame with two dedicated Cypher statements
val nodesQuery = "match (n:Person) RETURN id(n) as id, n.name as value UNION ALL MATCH (n:Company) return id(n) as id, n.name as value"
val relsQuery = "match (p:Person)-[r]->(c:Company) return id(p) as src, id(c) as dst, type(r) as value"

val graphFrame = Neo4j(sc).nodes(nodesQuery,Map.empty).rels(relsQuery,Map.empty).loadGraphFrame