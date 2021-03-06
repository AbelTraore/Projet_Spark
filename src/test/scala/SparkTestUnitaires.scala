import SparkBigData.ss
import com.mysql.cj.x.protobuf.MysqlxResultset
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import org.apache.spark.sql.functions.lit
import com.holdenkarau.spark.testing._

trait SparkSessionProvider {
  val sst = SparkSession.builder()
    .master(master = "local[*]")
    .getOrCreate()
}

class SparkTestUnitaires extends AnyFlatSpec with  SparkSessionProvider { //with DataFrameSuiteBase {
  it should("instanciate a Spark Session") in {
    var Env : Boolean = true
    val sst =  SparkBigData.Session_Spark(Env)
  }

  it should("compare to data frame") in {
    val structure_df = List(
      StructField("Employé", StringType, true),
    StructField("salaire", IntegerType, true)
    )
    val data_df = Seq(
      Row("Abel", 140000),
      Row("Marie", 200000),
      Row("Louise", 150000),
      Row("Alice", 250000)
    )
    val df_source : DataFrame = sst.createDataFrame(
      sst.sparkContext.parallelize(data_df),
      StructType(structure_df)
    )
    df_source.show()
    val df_new : DataFrame = df_source.withColumn("salaire", lit(100000))
    df_new.show()
    //assert(df_source.columns.size === df_new.columns.size)
    //assert(df_source.count() === 4)
    assert(df_source.take(4).length === 4)
//assertDataFrameEquals(df_source, df_new)


  }


}
