package springcloud.helloworld.eureka.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RoutingSendDirect {
	private static final String EXCHANGE_NAME = "topic_logs";
	//路由关键字
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		//声明交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String[] routingKeys = new String[] {"in.fo", "warni.ng", "err.or",""};
		//发送消息
		for(String severity:routingKeys) {
			String message = "Send the message level:" + severity;
			channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
			System.out.println("[x] Sent " + severity + ":" + message);
		}
		channel.close();
		connection.close();
	}
	
}
