package master.org

import org.apache.spark.{SparkConf, SparkContext}

/*
 * 
 * Data Lab - graph database organization.
 * 
 */
/**
 * @PACKAGE_NAME: master.org
 * @Description: TODO
 * @author Yc-Ma
 * @date 2020/12/16 13:44
 *
 *
 */
object ObjTest {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("InitSpark").setMaster("local[*]")
    val sc = new SparkContext(conf)
    // 生成一个最简单的List集合RDD
    val rdd = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9,1, 2, 3, 4, 5, 6, 7, 8, 9))

    // 对集合的每个元素都乘以3
    val mappedRDD = rdd.map(3 * _)

    // 通过collect 查看结果
    val array = mappedRDD.collect();
    array.foreach(v=>println(v))
  }
}

