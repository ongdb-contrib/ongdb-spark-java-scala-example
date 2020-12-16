package master.org;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * @author Yc-Ma
 * @PACKAGE_NAME: master.org.master.PRE_TO_ORG
 * @Description: TODO
 * @date 2020/10/28 18:19
 */
public class SparkTest {

    public static void main(String[] args) {
        // bolt+routing://10.20.13.146:7687
        SparkConf conf = new SparkConf()
                .setAppName("neoTest")
                .setMaster("local[*]")
                .set("spark.driver.allowMultipleContexts", "true")
                // bolt+routing://10.20.13.146:7687
                .set("spark.neo4j.bolt.url", "bolt://10.20.0.157:7787")
                .set("spark.neo4j.bolt.user", "ongdb")
                .set("spark.neo4j.bolt.password", "ongdb%dev");

        JavaSparkContext sc = new JavaSparkContext(conf);
        System.out.println(sc.version());
    }
}

