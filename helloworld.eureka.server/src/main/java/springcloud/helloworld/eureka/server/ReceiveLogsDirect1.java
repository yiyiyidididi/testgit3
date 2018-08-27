package springcloud.helloworld.eureka.server;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLogsDirect1 {
	//交换器名称
	private static final String EXCHANGE_NAME = "topic_logs";
	//路由关键字
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//获取匿名队列名称
		String queueName = channel.queueDeclare().getQueue();
		String[] routingKeys = new String[] {"in.#", "warni.#", "err.#","*"};
		//根据路由关键字进行多重绑定
		for(String severity:routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, severity);
			System.out.println("ReceiveLogsDirect1 exchange:" +EXCHANGE_NAME+",queue:"+queueName+",BindRoutingKey:"+severity);
			
		}
		System.out.println("ReceiveLogsDirect1 [*] Waiting for messages. To exit press CTRL+C");
		Consumer consumer  = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				// TODO Auto-generated method stub
				String message = new String(body,"UTF-8");
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
		
	}
}
