import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Explain: kafka生产者
 * Author: linjianhai
 * Date: 2022/6/15 22:33
 */
public class CustomProducerTranactions {

    /**
     * 异步发送
     * @param args
     */
    public static void main(String[] args) {
        //0、配置
        Properties properties = new Properties();
        //连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.91.31:9092,192.168.91.32:9092");
        //指定key和value 的序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"TRANSACTIONAL_ID_01");

        //1、创建kafka生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        //初始化事务
        kafkaProducer.initTransactions();
        //开启事务
        kafkaProducer.beginTransaction();

        try {
            //2、发送数据
            for (int i = 0; i < 5; i++) {
                kafkaProducer.send(new ProducerRecord<String, String>("first","我第来了" + i));
            }
            //提交事务
            kafkaProducer.commitTransaction();
        }catch (Exception e){
            //回滚事务
            kafkaProducer.abortTransaction();
        }finally {
            //3、关闭资源
            kafkaProducer.close();
        }
    }
}
