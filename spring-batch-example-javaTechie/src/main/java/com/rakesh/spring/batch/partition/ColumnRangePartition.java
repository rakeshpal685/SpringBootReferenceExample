package com.rakesh.spring.batch.partition;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import java.util.HashMap;
import java.util.Map;

/*This class is created so that when I am running multiple threads inside taskExecutor(), then rather than the threads picking the tasks and pushing the
    records to DB asynchronously, each thread will pick a chunk of the records from the csv*/
public class ColumnRangePartition implements Partitioner {


    private static Map<String, ExecutionContext> getStringExecutionContextMap(int gridSize, int min) {
        int max = 1000;
        int targetSize = (max - min) / gridSize + 1;//500
//        System.out.println("targetSize : " + targetSize);
        int number = 0;
        int start = min;
        int end = start + targetSize - 1;
        //1 to 500
        // 501 to 1000
        Map<String, ExecutionContext> result = new HashMap<>();
        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }
        return result;
    }

    /**
     * Create a set of distinct {@link ExecutionContext} instances together with a unique
     * identifier for each one. The identifiers should be short, mnemonic values, and only
     * have to be unique within the return value (e.g. use an incrementer).
     *
     * @param gridSize the size of the map to return
     * @return a map from identifier to input parameters
     */

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int min = 1;
        return getStringExecutionContextMap(gridSize, min);
        // System.out.println("partition result:" + result.toString());


   /* @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.put("partitionId", "partition" + i);
            result.put("partition" + i, context);
        }

        return result;*/
    }
}