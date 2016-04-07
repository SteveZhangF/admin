package com.oncore.data.messagequeue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncore.common.model.TableElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by steve on 3/17/16.
 */

/**
 * send table element to JMS queue
 * middleware will
 * create the related table,
 * create related groovy dao file,
 * create related database trigger
 * based on the tableElement
 *
 * */
@Component("tableBuilderMessageSender")
public class TableBuildMessageSender extends ProducerService<TableElement> {

    @Autowired
    public TableBuildMessageSender(JmsTemplate jmsTemplate, @Qualifier("tableCreatorDestination") javax.jms.Destination destination) {
        super(jmsTemplate);
        this.destination = destination;
        log = LogFactory.getLog(TableBuildMessageSender.class);
    }

    @Override
    protected MessageCreator getMessageCreator(final TableElement message) {
        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMapper mapper = new ObjectMapper();
                StringWriter stringWriter = new StringWriter();
                try {
                    mapper.writeValue(stringWriter, message);
                    return session.createTextMessage(stringWriter.toString());
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return null;
                }

            }
        };
    }

}
