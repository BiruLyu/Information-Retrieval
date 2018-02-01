package com.birulyu.invertedIndex;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class InvertedIndex {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if(args.length != 2){
			System.err.println("Usage: Inverted Index <input_path> <output_path>");
			System.exit(-1);
		}
		Job job = new Job();
		job.setJarByClass(InvertedIndex.class);
		job.setJobName("Inverted Index");
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(InvertedIndexMapper.class);
		job.setReducerClass(InvertedIndexReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.waitForCompletion(true);
	}

}
