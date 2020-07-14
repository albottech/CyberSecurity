#!/usr/bin/env python
# coding: utf-8

# # Netflow Analytics

# ## Apache Cassandra and Apache Spark Initialization

# In[1]:


# Configuratins related to Cassandra connector & Cluster
import os
os.environ['PYSPARK_SUBMIT_ARGS'] = '--packages com.datastax.spark:spark-cassandra-connector_2.11:2.3.0 --conf spark.cassandra.connection.host=127.0.0.1 pyspark-shell'


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


from pyspark.sql.types import *
from pyspark.sql.functions import * 


# ## Intialized cassandra connection pools for each host in the cluster. Instances of this class should not be created directly

# In[4]:


# Intialized cassandra connection pools for each host in the cluster. Instances of this class should not be created directly
from cassandra.cluster import Cluster

cluster = Cluster(['127.0.01'])

session = cluster.connect()


# ## Created a Function to Loads and get table that has a dataframe

# In[5]:


def load_and_get_table_df(keys_space_name, table_name):
    table_df = sqlContext.read        .format("org.apache.spark.sql.cassandra")        .options(table=table_name, keyspace=keys_space_name)        .load()
    return table_df


# ## Load the data from the cassandra table and keyspace
# 

# In[6]:


df = load_and_get_table_df("attack", "netflow")


# In[7]:


df.show(5)


# In[8]:


# Get no. of rows.
df.count()


# ## Accumulated the extracted data into a spark dataframe

# In[9]:


conn= spark.read.format("org.apache.spark.sql.cassandra").options(table="netflow", keyspace="attack").load()


# In[10]:


conn.count()


# In[11]:


# Convert into pandas dataframe

conn.limit(4).toPandas()


# ## Quantitative and Visual EDA

# ### Data Exploration

# In[12]:


# Import the Visualization packages
get_ipython().run_line_magic('matplotlib', 'inline')
import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns
# Seaborn palette setting 
plt.rcParams['figure.figsize'] = [10, 5]
sns.set_palette("deep", desat=.6)
sns.set_context("notebook")
sns.set_style("whitegrid")
sns.set(font= 'serif', font_scale=0.75)


# ## Import plotly and cuflinks required packages

# In[13]:


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


# In[14]:


# Convert into pandas dataframe

conn.limit(4).toPandas()


# ### Dataset is so large ---> Getting only sample data set out of it for the analysis
# 

# In[15]:


connSample = conn.sample(True, 0.05).toPandas()


# ## Univariate Analysis

#  ### Univariate Analysis on Attack Features

# In[16]:


(connSample.class_attack).iplot(kind='hist', bins=30 , color='navy' , linecolor='black',  xTitle='class_attack'  , title= 'Frequent Cyber Attacks ' ,   yTitle='Count' , showlegend=True)


# ## Multivariate Analysis

# In[17]:


# Dynamic plots using the plotly library
import plotly.plotly as py
import plotly.graph_objs as go
import plotly
plotly.offline.init_notebook_mode(connected=False)
import plotly.offline as py


trace_a = go.Box(x=connSample['class_attack'],
                y=connSample['protocol_type'],
                name='protocol_type',
                marker=dict(color='#FF4136'))

trace_b = go.Box(x=connSample['class_attack'],
                y=connSample['service'],
                name='service',
                marker=dict(color='#3D9970'))

data3 = go.Data([trace_a, trace_b])

layout = go.Layout(
    title='Identifying which Port Utilized by which Protocol Category' ,
    yaxis=dict(
        autorange=True,
        showgrid=True,
        zeroline=True,
        dtick=5,
        gridcolor='rgb(255, 255, 255)',
        gridwidth=1,
        zerolinecolor='rgb(255, 255, 255)',
        zerolinewidth=2,
    ))

fig = go.Figure(data = data3, layout=layout)
py.iplot(fig)


# In[18]:


#Creating some dynamic plots using the plotly library
import plotly
plotly.offline.init_notebook_mode(connected=True)
import plotly.offline as py
labels = connSample['class_attack'].value_counts().index
values = connSample['class_attack'].value_counts().values

colors = ['#eba796', '#96ebda']

fig = {'data' : [{'type' : 'pie',
                  'name' : "Cyber Attack State: Pie chart",
                 'labels' : connSample['class_attack'].value_counts().index,
                 'values' : connSample['class_attack'].value_counts().values,
                 'marker' : {'colors' : ['#F0E442', '#A2D5F2']}}], 
                 'layout' : {'title' : 'Cyber Attack State'}}


py.iplot(fig)


# ### Univariate analysis on protocol_type

# In[19]:


# Statistics on field protocol_type
conn.describe('protocol_type').toPandas()


# In[20]:


# Statistics on sample data
connSample.protocol_type.describe()


# In[21]:



(connSample.protocol_type).iplot(kind='hist', bins=30 , color='plum' , linecolor='black',  xTitle='Protocol_Type' ,  orientation = 'v' , title= 'Frequent Protocol Category' ,   yTitle='Count' , showlegend=True)


# In[22]:


print("Percentages of protocol_type: ")
print(1.0*connSample.protocol_type.value_counts()/len(connSample.protocol_type))


# ### Univariate analysis on Service

# In[23]:


# Statistics on service data
conn.describe('service').toPandas()


# In[24]:


# Get all different values
conn.freqItems(['service']).collect()[0]


# In[25]:


connSample.service.describe()


# In[26]:


(connSample.service).iplot(kind='hist', bins=30 , color='Crimson' , linecolor='black',  xTitle='Service' , title= 'Top Abnormally Response of Service on Destination Port' ,   yTitle='Count' , showlegend=True)


# In[27]:


print("Percentages of service's value:")
print(1.0*connSample.service.value_counts()/len(connSample.service))


# ### Univarite analysis on Flag

# In[28]:


#Statistics on flag data
conn.describe('flag').toPandas()


# In[29]:


# Getting all different values
conn.freqItems(['flag']).collect()[0]


# In[30]:


# Get stats with the Sample
connSample.flag.describe()


# In[31]:


(connSample.flag).iplot(kind='hist', bins=30 ,  color='DarkTurquoise' , linecolor='Brown'  ,   xTitle='Count' , title= 'Top Normal/Error Status of Connection' , orientation = 'h' ,   yTitle='Flag' , showlegend=True)


# In[32]:


connSample.flag.value_counts()


# In[33]:


print("Percentages of flag's value:")
print(1.0*connSample.flag.value_counts()/len(connSample.flag))


# ### Univariate Analysis on duration

# In[34]:


# Statistics on the duration field
conn.describe('duration').toPandas()


# In[35]:


connSample.duration.describe()


# sns.distplot(connSample.duration, bins = 10 , color="m" ,  rug=True );

# ### Skew distribution ---> log transformation

# In[36]:


# Log transformation(Skewness Distribution)
np.log(connSample.duration+1).iplot(kind='line', xTitle='Duration', color = 'purple', linecolor='Brown' ,
                  yTitle='Count', title='Length(number of seconds)of the Connection(Mostly values are zeros)' , showlegend=True)


# ### Scrutinize how many values equals to 0

# In[37]:


print("Numbers of 0's in duration feature: ", len(connSample.duration[connSample.duration == 0]))
print("The percentage of 0's in duration feature:", 100.0*len(connSample.duration[connSample.duration == 0])/len(connSample.duration))


# - Conclusions: Univariate Analysis Duration ---> 92% of the values are 0's

# ### Scrutinize the distribution without 0

# In[38]:


np.log(connSample.duration[connSample.duration>0]+1).describe()


# In[39]:


#Scrutinize the distribution(value=!0) without 0
np.log(connSample.duration[connSample.duration>0]+1).iplot(kind='hist', xTitle='Duration', color = 'blue', linecolor='blue' ,
                  yTitle='Count', title='Length (number of seconds) of Connection(log transformation without 0 seems Bimodal Distribution)' , showlegend=True)


# ### Univariate Analysis on src_bytes

# In[40]:


# Statistics on src_bytes data
conn.describe('src_bytes').toPandas()


# In[41]:


# Stats applied on sample one
connSample.src_bytes.describe()


# In[42]:


# Log Transformation
np.log(connSample.src_bytes).iplot(kind='hist', xTitle='src_bytes', color = 'red', linecolor='brown' ,
                  yTitle='Count', title='Number of data bytes from source to destination' , showlegend=True)


# ### Log Transformation and Skewness on src_bytes

# In[43]:


np.log(connSample.src_bytes+1).describe()


# In[44]:


#Scrutinize log transformation
np.log(connSample.src_bytes+1).iplot(kind='hist', xTitle='src_bytes',  color = 'darkorange' , linecolor='black',  title= ' Number of Data Bytes from Source to Destination(log transformation having bimodal distribution) ' , 
                  yTitle='count' , showlegend=True)


# ### Univarite Analysis on dst_bytes

# In[45]:


#Statistics with the dst_bytes data
conn.describe('dst_bytes').toPandas()


# In[46]:


#Log transformation
conn.select(log(conn['dst_bytes']+1)).describe().toPandas()


# In[47]:


# Skewness distribution -> log transformation
np.log(connSample.dst_bytes+1).describe()


# In[48]:


# Scrutinize the distribution
np.log(connSample.dst_bytes+1).iplot(kind='hist', xTitle='dst_bytes',  color = 'dodgerblue' , linecolor='black',  title= ' Data Bytes from Destination to Source (Bimodal Distribution Right-Skewed)' , 
                  yTitle='count' , showlegend=True)


# ### Univariate Analysis on land

# In[49]:


# Statistics on land data
conn.describe('land').toPandas()


# In[50]:


# Stats on sample data
connSample.land.describe()


# In[51]:


connSample.land.iplot(kind='hist', xTitle='Land', barmode='stack' , color='Goldenrod', title= ' Unusual Connections between same host/port with two values: 0 and 1 (Higher than average data transfer ) ' ,
                linecolor = 'brown' ,   yTitle='Count' , showlegend=True)


# In[52]:


connSample.land.value_counts()


# In[53]:


print("Percentages of lands's value:")
print(1.0*connSample.land.value_counts()/len(connSample.land))


# - Conclusion: Land is a discrete feature with two values: 0 and 1. Thus 99.98% of the instances have the value 0.

# ### Univariate Analysis on wrong_fragment

# In[54]:


#Statistics  on the data
conn.describe('wrong_fragment').toPandas()


# In[55]:


# Stats from sample data
connSample.wrong_fragment.describe()


# In[56]:


(connSample.wrong_fragment).iplot(kind='hist', bins=30 ,  color='Green' , linecolor='Black'  ,   xTitle='wrong_fragment' , title= ' Sum of bad checksum packets in a Connection continuous variable with only three values: 0, 1 and 3' ,   yTitle='Count' , showlegend=True)


# In[57]:


connSample.wrong_fragment.value_counts()


# In[58]:


print("Percentages of wrong_fragment's value:")
print(3.0*connSample.wrong_fragment.value_counts()/len(connSample.wrong_fragment))


# ### Univariate Analysis on urgent

# In[59]:


# Statistics on urgent field data
conn.describe('urgent').toPandas()


# In[60]:


connSample.urgent.describe()


# In[61]:


connSample.urgent.value_counts()


# In[62]:


print("Percentages of urgent's value:")
print(0.0*connSample.urgent.value_counts()/len(connSample.urgent))


# In[63]:


(connSample.urgent).iplot(kind='hist', bins=30 ,  color='LightSteelBlue' , linecolor='Black'  ,   xTitle='Urgent' , title= 'Number of Urgent packets Activated has continuous values with 0,1,2&3(99.99% instances value 0)' ,   yTitle='Count' , showlegend=True)


# ## Bivariate Analysis:

# ### Bivariate Analysis on duration and protocol_type

# In[64]:



data = []

for col in connSample['protocol_type'].unique():
    data.append(go.Box(y=connSample[connSample['protocol_type'] == col]['duration'], name=col))

iplot(data)


# - Conclusion: Regardless protocol_type feature most duration features are 0's.

# In[65]:


connSample['duration_log'] = np.log(connSample.duration+1)


# In[66]:


# Examine the distribution with duration_log > 0. Distributions of duration is very different between 'tcp' and 'udp'
connSample[connSample.duration_log > 0][[ "protocol_type"]].iplot(kind='hist' , color = "mediumpurple" , linecolor = 'black' ,  title= 'Top Protocol by Duration' ,  orientation = 'h' ,  xTitle='Duration_Log' ,  yTitle= 'Protocol_Type' , showlegend=True)


# In[67]:


# Scrutinize the distribution with duration_log == 0
connSample[connSample.duration_log == 0][[ "protocol_type"]].iplot(kind='hist' , color = "salmon" , linecolor = 'black' ,  title= ' Highest Protocol by Duration ' ,  orientation = 'v' ,  xTitle='Protocol_Type' ,  yTitle='Duration_Log' , showlegend=True)


# ### Bivariate Analysis on duration vs service analysis

# In[68]:


#Scrutinize the distribution with duration_log > 0
connSample[connSample.duration_log > 0]["service" ].iplot(kind='hist' , color = "darkolivegreen" , linecolor = 'darkolivegreen' ,  title= 'Most frequent Connectivity' , yTitle = 'Count'  ,  xTitle='Service' , showlegend=True)


# In[69]:


# Examine the distribution with duration_log == 0
connSample[connSample.duration_log == 0][[ "service"]].iplot(kind='hist' , color = "darkblue" , linecolor = 'darkblue' ,  title= 'Connectivity to Higher than Average Data Transfer' , xTitle= 'Service' ,  yTitle='Duration_log', showlegend=True)


# ### Bivariate Analysis on duration and flag

# In[70]:


#Examine the distribution with duration_log > 0
connSample[connSample.duration_log > 0][[ "flag"]].iplot(kind='line' , color = "purple" , linecolor = 'black' ,  title= 'Error Status of connection' ,   yTitle='Flag' , xTitle ='Duration_Log' , showlegend=True)


# In[71]:


#Examine the distribution with duration_log == 0
connSample[connSample.duration == 0][[ "flag"]].iplot(kind='hist' , color = "silver" , linecolor = 'black' , title = 'Top 3 Error Status Of the Connection' ,   xTitle='flag' , yTitle ='duration_log',showlegend=True)


# In[72]:


print("Percentages of flag's value:")
print(1.0*connSample[connSample.duration_log == 0].flag.value_counts()/len(connSample[connSample.duration_log == 0].flag))


# - Conclusions: duration & flag: Distribution duration different 'RSTR'&'RSTOS0' categories. duration_log-->0,3 values(SF,S0,REJ)of 11 concentrate 96% instances.

# ### duration vs src_bytes

# In[73]:



connSample['src_bytes_log'] = np.log(connSample.src_bytes+1)


# ### Inspecting with duration > 0

# In[74]:


# Scrutinize duration_log & src_bytes_logs > 0
connSample[connSample.duration>0][["duration_log", "src_bytes_log"]].iplot(kind='spread' , linecolor = 'black'  ,  title = 'Correlation among them not Strong(some clusters among them) ' ,  xTitle='duration_log' , yTitle =  'src_bytes_log' , showlegend=True)


# ### duration vs dst_bytes

# In[75]:



connSample['dst_bytes_log'] = np.log(connSample.dst_bytes+1)


# In[76]:


connSample[connSample.duration>0][["duration_log", "dst_bytes_log"]].iplot(kind='surface' , linecolor = 'black'  ,  title = 'Biomodal Distribution(Correlations are not strong)' , showlegend=True )


# In[77]:


# Scrunitize skewness when Land == 0
connSample[connSample.duration_log > 0][["duration_log" , "land"]].scatter_matrix()


# ### Duration vs Wrong_fragment Analysis:

# In[78]:


# Scrunitize skewness when wrong_fragment == 0
connSample[connSample.duration > 0][["duration_log" , "wrong_fragment" ]].iplot(kind = 'box' , linecolor = 'black'  ,  title = ' Normal Distribution' , showlegend=True )


# ### protocol_type vs service

# In[79]:


pivoted = connSample.pivot_table("class_attack", "service","protocol_type", aggfunc=len)


# In[80]:



iplot(pivoted.iplot(asFigure=True, kind='hist', title=' Top protocol by Service'  , dimensions=(600,400)))


# In[81]:


iplot(pivoted.iplot(asFigure=True, kind='box', dimensions=(600,400)))


# In[82]:


sns.set(font= 'serif', font_scale=0.65, rc={"figure.figsize": (3, 10)})
sns.heatmap(connSample.pivot_table("class_attack", "service","protocol_type", aggfunc=len), square= False,annot=True, annot_kws={"size": 7}, fmt=".0f", linewidths= .5);


# - Conclusions protocol_type vs service analysis: Except two general services ---> ('other' and 'private') all the services categories belong to a protocol_type category

# ### protocol_type vs flag

# In[83]:


sns.set(font= 'serif', font_scale=0.65, rc={"figure.figsize": (3, 6)})
sns.heatmap(connSample.pivot_table("class_attack", "flag","protocol_type", aggfunc=len), annot=True, fmt=".0f");


# - Conclusions: protocol_type vs flag analysis- All flag categories belongs to 'tcp' protocol_type except 'SF' flag that belongs to the three protocol_types

# In[84]:



trace_a = go.Box(x=connSample['class_attack'],
                y=connSample['flag'],
                name='flag',
                marker=dict(color='#E69F00'))

trace_b = go.Box(x=connSample['class_attack'],
                y=connSample['service'],
                name='service',
                marker=dict(color='#FF4136'))

data3 = go.Data([trace_a, trace_b])

layout = go.Layout(
    title='Identifying service and Normal/Error status of Connection' ,
    yaxis=dict(
        autorange=True,
        showgrid=True,
        zeroline=True,
        dtick=5,
        gridcolor='rgb(255, 255, 255)',
        gridwidth=1,
        zerolinecolor='rgb(255, 255, 255)',
        zerolinewidth=2,
    ))

fig = go.Figure(data3, layout=layout)
py.iplot(fig)

