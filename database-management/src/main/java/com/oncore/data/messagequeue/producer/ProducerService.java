package com.oncore.data.messagequeue.producer;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;

/**
 * Created by steve on 3/17/16.
 */

/**
 * an abstract class for JMS sender
 * */
public abstract class ProducerService<T> {

    JmsTemplate jmsTemplate;
    protected Log log;

    /**
     * inject the jmsTemplate
     * */
    @Autowired
    public ProducerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // destination of jms queue
    protected Destination destination;

    // return the specific message creator
    protected abstract MessageCreator getMessageCreator(T message);
    //send the message
    public void sendMessage(final T message) {
        jmsTemplate.send(destination, getMessageCreator(message));
        log.info("sent message "+ message.toString());
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
