package com.birulyu.invertedIndex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {
	
	@Override
	protected void reduce(Text word, Iterable<Text> docIds, Context context) throws IOException, InterruptedException {
		
		Map<String, Integer> map = new HashMap<>();
		for (Text docId : docIds) {
			String docIdStr = docId.toString();
			int count = map.getOrDefault(docIdStr, 0) + 1;
			map.put(docIdStr, count);
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> cur = it.next();
			if (sb.length() > 0) {
				sb.append("\t");
			}
			sb.append(cur.getKey() + ":"+ cur.getValue());
		}
		Text documentList = new Text(sb.toString());
		context.write(word, documentList);
		
		
	}
}
