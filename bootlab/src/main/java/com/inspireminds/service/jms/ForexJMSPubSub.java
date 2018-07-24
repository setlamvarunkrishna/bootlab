package com.inspireminds.service.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inspireminds.entity.ExchangeValue;
import com.inspireminds.repository.local.DataCacheServiceLocal;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@Service("forexJMSPubSub")
public class ForexJMSPubSub implements MessageListener {
	
	@Autowired
	private DataCacheServiceLocal dataCacheService;

	public DataCacheServiceLocal getDataCacheService() {
		return dataCacheService;
	}

	public void setDataCacheService(DataCacheServiceLocal dataCacheService) {
		this.dataCacheService = dataCacheService;
	}


	private MessageConsumer responseConsumer;
	private Session session;
	private XStream xstream;

	static {
		System.out.println("currencyConversionJMSPublisher started ************************* ");
	}

	public ForexJMSPubSub() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("CCS.TO.FOREX");

			// Consumer
			responseConsumer = session.createConsumer(destination);
			responseConsumer.setMessageListener(this);

			xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias("ExchangeValue", ExchangeValue.class);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to complete onMessage : %s, %s, %s", t.getMessage(), t.getCause(), t.getStackTrace()));
		}

	}

	public void onMessage(Message message) {
		try {
			System.out.println(String.format("Message Received :%s", message));
			//
			Destination destination = message.getJMSReplyTo();

			ExchangeValue exchangeValue = getDataCacheService().findByFromAndTo("EUR", "INR");
			System.out.println(String.format("Data found :%s", exchangeValue));
			// String jsonText = message.getObjectProperty("abc");
			TextMessage replyMsg = session.createTextMessage(String.valueOf(exchangeValue.getConversionMultiple().doubleValue()));
			System.out.println(String.format("Reply Queue Name is :%s", destination.toString()));
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			producer.send(replyMsg);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to complete onMessage : %s, %s, %s", t.getMessage(), t.getCause(), t.getStackTrace()));

		}
	}
}
