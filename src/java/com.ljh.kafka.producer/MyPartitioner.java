import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Explain: 自定义分区器：内容为 ljh 发送到 0分区 ，其他发送到 1分区
 * Author: linjianhai
 * Date: 2022/6/15 23:20
 */
public class MyPartitioner implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object values, byte[] bytes1, Cluster cluster) {
        String msgValues = values.toString();
        int partition;
        if (msgValues.contains("ljh")){
            partition = 0;
        }else {
            partition = 1;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
