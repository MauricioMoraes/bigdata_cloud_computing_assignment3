Big Data and Cloud Computing - Assignment 3
====================

Site:
http://jhm-assignment.elasticbeanstalk.com/home

Team:
* Juliana Louback - jl4354
* Henrique Spyra Gubert - hs2807 
* Radu Michael Moldoveanu - rmm2231

**NOTE**: We implemented the analytics to see how sentiment evolves over time for extra points.

# Contents

* **web_interface**: web application to show all analytics.
* **sentiment_analysis**: folder with the map reduce programs to extract trends and sentiment timeseries.
* **find_top_trends**: java program to process map reduce job output and prepare for visualization.
* **find_top_positive_and_negative_keywords**: java program to process map reduce job output and prepare for visualization.
* **merge_sentiment_timeseries**: java program to process map reduce job output and prepare for visualization.
* **copy_tweet_data**: program to transfer tweet raw data to our bucket.
* **s3_simple_reader**: program to output first lines of file in S3 without downloading it.
* **Tomcat**: folder where Tomcat is installed, so the whole assignment is auto-contained.
* **Servers**: eclipse servers for testing and debugging locally.

# Screenshots

## General Trends
<img src="https://raw.githubusercontent.com/hsgubert/bigdata_cloud_computing_assignment3/master/general_trends.png">

## Positive/Negative Trends
<img src="https://raw.githubusercontent.com/hsgubert/bigdata_cloud_computing_assignment3/master/positive_negative_trends.png">

## Sentiment Timeseries
<img src="https://raw.githubusercontent.com/hsgubert/bigdata_cloud_computing_assignment3/master/sentiment_timeseries.png">

