package edu.columbia.hs2807;



import java.io.IOException;

import java.util.*;

import java.util.regex.Pattern;

import java.util.regex.Matcher;



import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapreduce.Mapper.Context;



// Counts the words in tweets. Ignores stop words and lines that are not tweets.



public class Trender {



	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);

		private Text word = new Text();



		private static final String[] stopWordsArray = new String[] {"a","about","across","after","ain","all","almost","also","am","among","an","and","any","are","aren","as","at","be","because","been","but","by","can","cannot","could","couldn","did","didn","do","does","doesn","don","either","else","ever","every","for","from","get","got","had","has","hasn","have","he","her","hers","him","his","how","however","i","if","in","into","is","isn","it","its","just","least","let","may","me","might","mightn","most","must","mustn","my","neither","no","nor","not","of","off","on","only","or","other","our","own","said","say","says","shan","she","should","shouldn","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was","wasn","we","were","weren","what","when","when","where","which","while","who","whom","why","s","will","with","won","would","wouldn","yet","you","d","ll","re","ve","your"};



		private static final Pattern sTweetPattern = Pattern.compile("^@.+");

		private static final Pattern sTweetPrefixPattern = Pattern.compile("@\\w+");



		private HashSet<String> stopWords;



		@Override

		public void setup(Context context) throws IOException, InterruptedException {

			stopWords = new HashSet<String>();

			for (int i=0; i<stopWordsArray.length; i++) {

				stopWords.add(stopWordsArray[i]);

			}

		}



		@Override

	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

	      	String line = value.toString();

		

			// if it does not start with @ is not a tweet

			if (!sTweetPattern.matcher(line).matches()) {

				return;

			}



			// if it is a tweet, remove names 

			Matcher matcher = sTweetPrefixPattern.matcher(line);

			if (matcher.find()) {

				matcher.replaceAll("");

			}

			else {

				// did not identify format		

				return;

			}



			// tokenize and emit keywords that are not stop words

			String[] tokens = line.split("[\\W_\\d]+");

			for (int i=0; i<tokens.length; i++) {

				String token = tokens[i].toLowerCase();



				if (!stopWords.contains(token) && token.length() > 1) {

					word.set(token);

					context.write(word, one);

				}

			}

		}

  	}



	public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable> {

		@Override

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

			int sum = 0;

			Iterator<IntWritable> it=values.iterator();

			while (it.hasNext()) {

				sum += it.next().get();

			}

			context.write(key, new IntWritable(sum));

		}

	}



  public static void main(String[] args) throws Exception {

    Configuration conf = new Configuration();

    Job job = Job.getInstance(conf, "trender");



    job.setJarByClass(Trender.class);

    job.setMapperClass(Map.class);

    job.setCombinerClass(Reduce.class);

    job.setReducerClass(Reduce.class);

    job.setOutputKeyClass(Text.class);

    job.setOutputValueClass(IntWritable.class);



    FileInputFormat.addInputPath(job, new Path(args[0]));

    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);

  }

}

