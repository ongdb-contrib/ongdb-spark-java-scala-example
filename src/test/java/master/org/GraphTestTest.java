package master.org;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.spark.Neo4JavaSparkContext;
import org.neo4j.spark.Neo4jGraph;
import scala.collection.Seq;
import scala.collection.Seq$;

import static junit.framework.TestCase.assertEquals;

/*
 *
 * Data Lab - graph database organization.
 *
 */

/**
 * @author Yc-Ma
 * @PACKAGE_NAME: master.org
 * @Description: TODO
 * @date 2020/12/10 11:02
 */
public class GraphTestTest {
    public static final String FIXTURE = "CREATE (:A)-[:REL]->(:B)";

    private static SparkConf conf;
    private static JavaSparkContext sc;
    private static Neo4JavaSparkContext csc;

    @Before
    public void setUp() throws Exception {
        conf = new SparkConf()
                .setAppName("neoTest")
                .setMaster("local")
                .set("spark.driver.allowMultipleContexts","true")
                .set("spark.neo4j.bolt.url", "server.boltURI().toString()");
        sc = new JavaSparkContext(conf);
        csc = Neo4JavaSparkContext.neo4jContext(sc);
    }

    @After
    public void tearDown() {
        sc.close();
    }

    @Test
    public void runMatrixQuery() {
        Seq<String> empty = (Seq<String>) Seq$.MODULE$.empty();
        Graph graph = Neo4jGraph.loadGraph(sc.sc(), "A", empty, "B");
        assertEquals(2, graph.vertices().count());
        assertEquals(1, graph.edges().count());
        System.out.println("Hello world!");
    }
}

