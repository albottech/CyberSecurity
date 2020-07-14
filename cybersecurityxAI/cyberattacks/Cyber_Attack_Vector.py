
# coding: utf-8

# # Built a Predictive Model to Identify different types of Cyber Attacks.
# 

# ## Apache Cassandra and Apache Spark Initialization

# In[1]:

# Configuratins related to Cassandra connector & Cluster
import os
os.environ['PYSPARK_SUBMIT_ARGS'] = '--packages com.datastax.spark:spark-cassandra-connector_2.11:2.3.0 --conf spark.cassandra.connection.host=127.0.0.1 pyspark-shell'


# ### Onboard required packages

# In[2]:

# Import required Libraries 
import cassandra
import pyspark
from pyspark.context import SparkContext
from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession
sc = pyspark.SparkContext(appName="SecurityAnalytics")
spark = SparkSession(sc)
from pyspark.sql import SQLContext
sqlContext = SQLContext(sc)


# In[3]:

get_ipython().magic('matplotlib inline')
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import pandas as pd


# In[4]:

from pyspark.sql.types import *
from pyspark.sql.functions import * 


# ### Initialized Cassandra Connection Pools for each Host in the Cluster. Instances of this class should not be created directly

# In[5]:

from cassandra.cluster import Cluster

cluster = Cluster(['127.0.01'])

session = cluster.connect()


# ### Created a Function to Load and get table that has a dataframe

# In[6]:

def load_and_get_table_df(keys_space_name, table_name):
    table_df = sqlContext.read        .format("org.apache.spark.sql.cassandra")        .options(table=table_name, keyspace=keys_space_name)        .load()
    return table_df


# ### Load the data from the cassandra keyspace & table

# In[7]:

df = load_and_get_table_df( "test" , "attack")


# In[8]:

df.show(5)


# In[9]:

# Get no. of rows.
df.count()


# ### Accumulated the extracted data into a spark dataframe

# In[10]:

conn= spark.read.format("org.apache.spark.sql.cassandra").options(table="attack", keyspace="test").load()


# In[11]:

conn.count()


# In[12]:

# Convert into pandas dataframe

conn.limit(4).toPandas()


# ## Data Engineering

# ### Data Preparation

# ### Conversion from Categorical variables to Integer variables

# In[13]:

# Encoded protocol feature
# Distinct values of class_attack

conn.select("protocol_type").distinct().toPandas()


# In[14]:

categories=conn.select("protocol_type").distinct().toPandas()["protocol_type"]


# In[15]:

categories


# In[16]:

dictCategories = dict((v,int(k)) for (k,v) in categories.to_dict().items())


# In[17]:

dictCategories


# In[18]:

from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[19]:


connEncoded = conn.withColumn("protocol_type", udfCategoriesToInt("protocol_type") )


# In[20]:

connEncoded.limit(10).toPandas()


# In[21]:

# Encoding service

connEncoded.select("service").distinct().toPandas()


# In[22]:

categories1 =connEncoded.select("service").distinct().toPandas()["service"]


# In[23]:

categories1


# In[24]:

dictCategories1 = dict((v,int(k)) for (k,v) in categories1.to_dict().items())


# In[25]:

dictCategories1 


# In[26]:

from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories1[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[27]:

connEncoded1 = connEncoded.withColumn("service", udfCategoriesToInt("service") )


# In[28]:

connEncoded1.limit(10).toPandas()


# In[29]:

# Encoding src_bytes feature
connEncoded1.select("src_bytes").distinct().toPandas()


# In[30]:

categories2 =connEncoded1.select("src_bytes").distinct().toPandas()["src_bytes"]


# In[31]:

categories2


# In[32]:

dictCategories2 = dict((v,int(k)) for (k,v) in categories2.to_dict().items())


# In[33]:

dictCategories2


# In[34]:

from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories2[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[35]:

connEncoded2 = connEncoded1.withColumn("src_bytes", udfCategoriesToInt("src_bytes") )


# In[36]:

connEncoded2.limit(10).toPandas()


# In[37]:

# Encoding class_attack feature
connEncoded2.select("class_attack").distinct().toPandas()


# In[38]:

categories3 =connEncoded2.select("class_attack").distinct().toPandas()["class_attack"]


# In[39]:

categories3


# In[40]:

dictCategories3 = dict((v,int(k)) for (k,v) in categories3.to_dict().items())


# In[41]:

dictCategories3


# In[42]:

from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories3[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[43]:


connEncoded3 = connEncoded2.withColumn("class_attack", udfCategoriesToInt("class_attack") )


# In[44]:


connEncoded3.limit(10).toPandas()


# In[45]:

connEncoded3.columns


# In[46]:

connEncoded3.describe()


# In[47]:

netflow = connEncoded3.columns
netflow


# In[48]:

# Converting the type of the feature "label" from integer to double
netflow = connEncoded3.withColumn('class_attack', when(connEncoded3['class_attack'] == 4, 0.0).otherwise(1.0))


# In[49]:

netflow.cache()


# In[50]:

netflow.limit(5).toPandas()


# In[51]:

netflow.columns


# ## Feature Selection

# In[52]:

netflow.columns


# In[53]:

#List of all the columns
col=netflow.columns
col.remove("class_attack")
col


# ## Vectorization

# In[54]:

# Vectorizing the input features

from pyspark.ml.feature import VectorAssembler
conn_vect = VectorAssembler(inputCols = col, outputCol="features").transform(netflow)
conn_vect.select("features", "class_attack").limit(5).toPandas()


# ### Scaling

# In[55]:

#Applying Min-Max scaling
from pyspark.ml.feature import MinMaxScaler
scaler = MinMaxScaler(inputCol="features", outputCol="minmax_scaled_features")


# In[56]:

mm = scaler.fit(conn_vect)
conn_scale = mm.transform(conn_vect)
conn_scale.select("minmax_scaled_features", "class_attack").limit(5).toPandas()


# ## Splitting the dataset

# In[57]:

conn_train, conn_test = conn_scale.randomSplit(weights=[0.7, 0.3], seed=1)
print("Number of observation in train-",conn_train.count())
print("Number of observation in test-",conn_test.count())


# In[58]:

conn_train.count()


# In[59]:


conn_test.count()


# ### Training Decision Tree Classifier

# In[60]:


from pyspark.ml import Pipeline
from pyspark.ml.classification import DecisionTreeClassifier
from pyspark.ml.feature import StringIndexer, VectorIndexer
from pyspark.mllib.evaluation import BinaryClassificationMetrics
from pyspark.mllib.evaluation import MulticlassMetrics


# In[61]:

# Train DecisionTree model.

dt = DecisionTreeClassifier( featuresCol = 'minmax_scaled_features', labelCol = 'class_attack')


# In[62]:

dtModel = dt.fit(conn_train)


# ### Make predictions on the test set
# 

# In[63]:

# Run prediction on the whole dataset
conn_test_pred = dtModel.transform(conn_test)


# In[64]:

conn_test_pred.limit(3).toPandas()


# In[65]:

# Select example rows to display.

conn_test_pred.select("prediction", "class_attack" , "minmax_scaled_features").limit(10).toPandas()


# In[66]:

# Compute raw scores on the test set

predictionAndLabels = conn_test_pred.select("prediction", "class_attack").rdd


# In[67]:

predictionAndLabels.take(3)


# ## Confusion Matrix

# In[68]:

# Instantiate Basic Metrics object

basicMetrics = MulticlassMetrics(predictionAndLabels)


# In[69]:

print("Summary Stats")
print("Weighted Precision = %s" % basicMetrics.weightedPrecision)
print("Weighted Recall = %s" % basicMetrics.weightedRecall)
print("Weighted F1 Score = %s" % basicMetrics.weightedFMeasure())
print("Confusion Matrix:")
pd.DataFrame(basicMetrics.confusionMatrix().toArray())


# In[70]:

conn_test_pred.groupBy("class_attack").pivot("prediction").count().show()


# 
# ## Evaluation on Decision Tree model
# 

# In[71]:


# Concretize Advanced Metrics object

advMetrics = BinaryClassificationMetrics(predictionAndLabels)


# In[72]:


# Area under precision-recall curve

print("Area under PR = %s" % advMetrics.areaUnderPR)


# In[73]:

# Area under ROC curve

print("Area under ROC = %s" % advMetrics.areaUnderROC)


# In[74]:

# Evaluate
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

evaluator = MulticlassClassificationEvaluator(labelCol="class_attack",  metricName="accuracy", predictionCol="prediction")
evaluator.evaluate(conn_test_pred)


# - Conclusion : DT performed nicely because it is too strong given the range of different features. The prediction accuracy of decision trees is 99%

# In[75]:

print(dt.explainParams())


# ## Training random Forest classifier

# In[76]:


from pyspark.ml import Pipeline
from pyspark.ml.classification import RandomForestClassifier
from pyspark.ml.feature import StringIndexer, VectorIndexer
from pyspark.ml.evaluation import MulticlassClassificationEvaluator
from pyspark.mllib.evaluation import MulticlassMetrics
from pyspark.mllib.evaluation import BinaryClassificationMetrics


# In[77]:

rf = RandomForestClassifier(labelCol="class_attack", featuresCol="minmax_scaled_features", seed = 1234 , numTrees = 10, maxDepth = 5, impurity = "gini")


# In[78]:

rf_model = rf.fit(conn_train)


# ## Make predictions on the test set
# 

# In[79]:


# Run prediction on the whole dataset
conn_test_pred1 = rf_model.transform(conn_test)


# In[80]:


conn_test_pred1.limit(5).toPandas()


# In[81]:

# Select example rows to display.

conn_test_pred1.select("prediction", "class_attack" , "minmax_scaled_features").limit(10).toPandas()


# In[82]:

# Compute raw scores on the test set

predictionAndLabels = conn_test_pred1.select("prediction", "class_attack").rdd


# In[83]:

# Compute raw scores on the test set

predictionAndLabels.take(5)


# ## Confusion matrix

# In[84]:

# Instantiate Basic Metrics object

basicMetrics = MulticlassMetrics(predictionAndLabels)
print("Summary Stats")
print("Weighted Precision = %s" % basicMetrics.weightedPrecision)
print("Weighted Recall = %s" % basicMetrics.weightedRecall)
print("Weighted F1 Score = %s" % basicMetrics.weightedFMeasure())
print("Confusion Matrix:")
pd.DataFrame(basicMetrics.confusionMatrix().toArray())


# In[85]:

conn_test_pred1.groupBy("class_attack").pivot("prediction").count().show()


# ## Evaluate on Random Forest¶

# In[86]:

# Instantiate Advanced Metrics object

advMetrics = BinaryClassificationMetrics(predictionAndLabels)


# In[87]:

# Area under precision-recall curve

print("Area under PR = %s" % advMetrics.areaUnderPR)


# In[88]:

# Area under ROC curve

print("Area under ROC = %s" % advMetrics.areaUnderROC)


# In[89]:

from pyspark.ml.evaluation import MulticlassClassificationEvaluator

# Evaluate

evaluator = MulticlassClassificationEvaluator(labelCol="class_attack",  metricName="accuracy", predictionCol="prediction")
evaluator.evaluate(conn_test_pred)


# In[90]:

print(rf.explainParams())


# ## Training NaiveBayes Classifier

# In[91]:

from pyspark.ml.classification import NaiveBayes
from pyspark.ml.evaluation import MulticlassClassificationEvaluator


# In[92]:

nb = NaiveBayes(smoothing=1.0, modelType="multinomial" , labelCol="class_attack", featuresCol="minmax_scaled_features")


# In[93]:

model = nb.fit(conn_train)


# ### Make Prediction on Test Set
# 

# In[94]:


# Run prediction on the whole dataset


conn_test_pred2 = model.transform(conn_test)



# In[95]:



conn_test_pred2.limit(5).toPandas()


# In[96]:

# Select example rows to display.

conn_test_pred2.select("prediction", "class_attack" , "minmax_scaled_features").limit(10).toPandas()


# In[97]:

# Compute raw scores on the test set

predictionAndLabels = conn_test_pred2.select("prediction", "class_attack").rdd


# In[98]:

# Compute raw scores on the test set

predictionAndLabels.take(5)


# ## Confusion Matrix

# In[99]:

# Instantiate Basic Metrics object

basicMetrics = MulticlassMetrics(predictionAndLabels)
print("Summary Stats")
print("Weighted Precision = %s" % basicMetrics.weightedPrecision)
print("Weighted Recall = %s" % basicMetrics.weightedRecall)
print("Weighted F1 Score = %s" % basicMetrics.weightedFMeasure())
print("Confusion Matrix:")
pd.DataFrame(basicMetrics.confusionMatrix().toArray())


# In[100]:

conn_test_pred1.groupBy("class_attack").pivot("prediction").count().show()


# In[101]:

# Evaluate NaiveBayes model
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

evaluator = MulticlassClassificationEvaluator(labelCol="class_attack",  metricName="accuracy", predictionCol="prediction")


# In[102]:

accuracy = evaluator.evaluate(conn_test_pred2)


# In[103]:

print("Test set accuracy = " + str(accuracy))


# ## Training Logistic regression
# 

# In[104]:


from pyspark.ml.classification import LogisticRegression
mlr = LogisticRegression(maxIter=10, labelCol="class_attack", featuresCol="minmax_scaled_features")


# In[105]:



# Fit the model
mlrModel = mlr.fit(conn_train)


# ## Make prediction on test set
# 

# In[106]:


#Run prediction on the whole dataset
conn_test_pred3 = mlrModel.transform(conn_test)


# In[107]:

conn_test_pred3.limit(5).toPandas()


# In[108]:

# Select example rows to display.

conn_test_pred3.select("prediction", "class_attack" , "minmax_scaled_features").limit(10).toPandas()


# In[109]:

# Compute raw scores on the test set

predictionAndLabels = conn_test_pred3.select("prediction", "class_attack").rdd


# In[110]:


# Compute raw scores on the test set

predictionAndLabels.take(5)


# ## Confusion Matrix

# In[111]:

# Instantiate Basic Metrics object

basicMetrics = MulticlassMetrics(predictionAndLabels)
print("Summary Stats")
print("Weighted Precision = %s" % basicMetrics.weightedPrecision)
print("Weighted Recall = %s" % basicMetrics.weightedRecall)
print("Weighted F1 Score = %s" % basicMetrics.weightedFMeasure())
print("Confusion Matrix:")
pd.DataFrame(basicMetrics.confusionMatrix().toArray())


# In[112]:

conn_test_pred3.groupBy("class_attack").pivot("prediction").count().show()


# ## Evaluate Logistic Regression Model

# In[113]:


#Evaluate
from pyspark.ml.evaluation import BinaryClassificationEvaluator
evaluator = BinaryClassificationEvaluator(labelCol="class_attack")
evaluator.evaluate(conn_test_pred3)

