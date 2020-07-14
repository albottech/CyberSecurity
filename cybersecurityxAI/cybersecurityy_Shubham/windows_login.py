#!/usr/bin/env python
# coding: utf-8

#  # Window Logging Analysis

# ### Configuration related to Cassandra connector & Cluster

# In[1]:


import os 
os.environ['PYSPARK_SUBMIT_ARGS'] = '--packages com.datastax.spark:spark-cassandra-connector_2.11:2.3.0 --conf spark.cassandra.connection.host=127.0.0.1 pyspark-shell'


# ### Importing all the required packages

# In[2]:


import cassandra
import pyspark
from pyspark.context import SparkContext
from pyspark.sql.context import SQLContext
from pyspark.sql.session import SparkSession
sc = SparkContext()
spark = SparkSession(sc)
from pyspark.sql import SQLContext
sqlContext = SQLContext(sc)


# ### Connecting to the cluster 

# In[3]:


from cassandra.cluster import Cluster

cluster = Cluster(['127.0.0.1'])

session = cluster.connect()


# ###  Create a Function to Loads and returns data frame for a table including key space given

# In[4]:


def load_and_get_table_df(keys_space_name, table_name):
    table_df = sqlContext.read        .format("org.apache.spark.sql.cassandra")        .options(table=table_name, keyspace=keys_space_name)        .load()
    return table_df


# ### Load the data from the cassandra table and keyspace

# In[5]:


data = load_and_get_table_df("test", "new")


# In[6]:


data.show()


# ### The count of the number of rows in the dataset 

# In[7]:


data.count()


# ### Storing the extracted data into a spark dataframe

# In[8]:


df1 = spark.read.format("org.apache.spark.sql.cassandra").options(table="new", keyspace="test").load()


# ### The number of rows after sending data to spark

# In[9]:


print (df1.count())


# ### Converted the spark dataframe to pandas to have a view of the dataset

# In[10]:


df=df1.toPandas()
df


# ### So, the columns authentication_type contain many question mark so replacing it.

# In[11]:


df2 = df1.replace('?', 'Kerberos' , 'authentication_type') 


# ### Replacing the question mark with in the logon_type with Network.

# In[12]:


df3 = df2.replace('?', 'Network' , 'logon_type')


# ### View of the dataset

# In[13]:


df4 = df3.toPandas()


# ### Data Visualization 

# In[14]:


# Standard plotly imports
import plotly.dashboard_objs as dashboard
import plotly.plotly as py
import plotly.figure_factory as ff
import plotly.graph_objs as go
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from plotly import tools
# Import plotly and  cufflinks modules to work with visualization in offline mode
import cufflinks
# Update to use cufflinks offline
cufflinks.go_offline(connected=True)
# Connect with java script to the notebook with init_notebook_mode() method
from plotly.offline import iplot, init_notebook_mode
init_notebook_mode(connected=False)

from IPython.display import HTML, Image


# In[15]:


# Windows Login State
import plotly
plotly.offline.init_notebook_mode(connected=False)
import plotly.offline as py

labels = df4['success_failure'].value_counts().index
values = df4['success_failure'].value_counts().values

colors = ['#eba796', '#96ebda']

fig = {'data' : [{'type' : 'pie',
                  'name' : "Window Logging State: Pie chart",
                 'labels' : df4['success_failure'].value_counts().index,
                 'values' : df4['success_failure'].value_counts().values,
                 'direction' : 'clockwise',
                 'marker' : {'colors' : ['#9cc359', '#e96b5c']}}], 'layout' : {'title' : 'Window Logging State'}}

py.iplot(fig)


# In[16]:


# Standard plotly imports
import plotly.plotly as py
import plotly.graph_objs as go
from plotly.offline import iplot, init_notebook_mode
# Using plotly + cufflinks in offline mode
import cufflinks
cufflinks.go_offline(connected=True)
init_notebook_mode(connected=False)

df4['authentication_type'].iplot(kind='hist', xTitle='authentication_type', 
                  yTitle='count', title='Authentication Type Distribution')


# In[17]:


# Standard plotly imports
import plotly.plotly as py
import plotly.graph_objs as go
from plotly.offline import iplot, init_notebook_mode
# Using plotly + cufflinks in offline mode
import cufflinks
cufflinks.go_offline(connected=True)
init_notebook_mode(connected=False)

df4['logon_type'].iplot(kind='hist', yTitle='logon_type', orientation = 'v' ,
                  xTitle='count', title='Logon Type Distribution' , color = 'red')


# In[18]:


# Unusual web activity from any login
data = []

for col in df4['logon_type'].unique():
    data.append(go.Box(y=df4[df4['logon_type'] == col]['time'], name=col))

iplot(data)


# ### Converting Categorical Features to Integer

# In[19]:


df3.select("authentication_orientation").distinct().toPandas()


# In[20]:


categories3 =df3.select("authentication_orientation").distinct().toPandas()["authentication_orientation"]


# In[21]:


categories3


# In[22]:


dictCategories3 = dict((v,int(k)) for (k,v) in categories3.to_dict().items())


# In[23]:


dictCategories3


# In[24]:


from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories3[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[25]:


df3_incoded = df3.withColumn("authentication_orientation", udfCategoriesToInt("authentication_orientation") )


# In[26]:


df3_incoded.limit(10).toPandas()


# In[27]:


df3_incoded.select("logon_type").distinct().toPandas()


# In[28]:


categories2 =df3_incoded.select("logon_type").distinct().toPandas()["logon_type"]


# In[29]:


categories2


# In[30]:


dictCategories2 = dict((v,int(k)) for (k,v) in categories2.to_dict().items())


# In[31]:


dictCategories2


# In[32]:


from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories2[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[33]:


df4_incoded = df3_incoded.withColumn("logon_type", udfCategoriesToInt("logon_type") )


# In[34]:


df4_incoded.limit(10).toPandas()


# In[35]:


df4_incoded.select("authentication_type").distinct().toPandas()


# In[36]:


categories1 =df4_incoded.select("authentication_type").distinct().toPandas()["authentication_type"]


# In[37]:


categories1


# In[38]:


dictCategories1 = dict((v,int(k)) for (k,v) in categories1.to_dict().items())


# In[39]:


dictCategories1


# In[40]:


from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType
def categoriesToInt(cat):
    return dictCategories1[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[41]:


df5_incoded = df4_incoded.withColumn("authentication_type", udfCategoriesToInt("authentication_type") )


# In[42]:


df5_incoded.limit(10).toPandas()


# In[43]:


from pyspark.sql.functions import udf
from pyspark.sql.types import IntegerType


# In[44]:


df5_incoded.select("success_failure").distinct().toPandas()


# In[45]:


categories =df5_incoded.select("success_failure").distinct().toPandas()["success_failure"]


# In[46]:


categories


# In[47]:


dictCategories = dict((v,int(k)) for (k,v) in categories.to_dict().items())


# In[48]:


dictCategories


# In[49]:


from pyspark.sql.types import DoubleType
def categoriesToInt(cat):
    return dictCategories[cat]

udfCategoriesToInt = udf(categoriesToInt, IntegerType())


# In[50]:


df6_incoded1 = df5_incoded.withColumn("success_failure", udfCategoriesToInt("success_failure") )


# In[51]:


df6_incoded1.limit(10).toPandas()


# In[52]:


df6_incoded1.columns


# In[53]:


# Converting the type of the feature "label" from integer to double
from pyspark.sql.types import DoubleType
window_dataset = df6_incoded1.withColumn('success_failure',df6_incoded1['success_failure'].cast('double'))


# In[54]:


window_dataset.limit(5).toPandas()


# In[55]:


window_dataset.columns


# ### Feature Selection

# In[56]:


# Feature Selection, Dropping Source computer, Source_user domain, Destination_computer and Destination_user_domain as it 
# Specific to each organisation network and generic model should not contain them.So,Droping those Column from the spark datafram
drop_list = ['source_computer', 'destination_user_domain' , 
 'destination_computer' , 'source_user_domain']
window_dataset=window_dataset.select([column for column in window_dataset.columns if column not in drop_list])        


# In[57]:


window_dataset.columns


# In[58]:


#List off all the columns
colum=window_dataset.columns
colum.remove("success_failure")
colum


# ### Vectorizing

# In[59]:


#Vectorizing the set of input features
from pyspark.ml.feature import VectorAssembler
df_vect = VectorAssembler(inputCols = colum, outputCol="features").transform(window_dataset)
df_vect.select("features", "success_failure").limit(5).toPandas()


# ### Scaling

# In[60]:


#Applying Min-Max scaling
from pyspark.ml.feature import MinMaxScaler
mm_scaler = MinMaxScaler(inputCol="features", outputCol="minmax_scaled_features")


# In[61]:


mm = mm_scaler.fit(df_vect)
df_scale = mm.transform(df_vect)
df_scale.select("minmax_scaled_features", "success_failure").limit(5).toPandas()


# ### Divinding the dataset

# In[62]:


df_train, df_test = df_scale.randomSplit(weights=[0.7, 0.3], seed=1)
print("Number of observation in train-",df_train.count())
print("Number of observation in test-",df_test.count())
#df_train.count(), df_test.count()


# ### Built  Logistic Regression Classifier

# In[63]:


from pyspark.ml.classification import LogisticRegression
lr = LogisticRegression(featuresCol = 'minmax_scaled_features', labelCol = 'success_failure', maxIter=10)
lrModel = lr.fit(df_train)


# ### Prediction Result

# In[64]:


#Run prediction on the whole dataset
df_test_pred = lrModel.transform(df_test)
df_test_pred.show()


# In[65]:


df_test_pred.limit(5).toPandas()


# ### Confusion Matrix

# In[66]:


df_test_pred.groupBy("success_failure").pivot("prediction").count().show()


# ### Evaluation and Accuracy

# In[67]:


#Evaluate
from pyspark.ml.evaluation import BinaryClassificationEvaluator
evaluator = BinaryClassificationEvaluator(labelCol="success_failure")
evaluator.evaluate(df_test_pred)


# ### Build  RandomForest Classifer

# In[68]:


import pyspark
from pyspark.ml.pipeline import Pipeline

from pyspark.ml.classification import RandomForestClassifier
from pyspark.ml import evaluation
from pyspark.sql.functions import *

forest = RandomForestClassifier(labelCol="success_failure", featuresCol="minmax_scaled_features", seed = 123)


# In[69]:


forest_model = forest.fit(df_train)


# ### Prediction Result 

# In[70]:


#Run prediction on the whole dataset
df_test_pred1 = forest_model.transform(df_test)
df_test_pred1.show()


# ### Confusion Metrics

# In[71]:


df_test_pred1.groupBy("Success_Failure").pivot("prediction").count().show()


# ### Evaluate

# In[72]:


#Evaluate
evaluator = evaluation.MulticlassClassificationEvaluator(labelCol="success_failure", 
                                        metricName="accuracy", predictionCol="prediction")
evaluator.evaluate(df_test_pred1)

