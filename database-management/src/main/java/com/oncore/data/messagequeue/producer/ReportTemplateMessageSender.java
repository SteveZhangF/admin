package com.oncore.data.messagequeue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oncore.data.messagequeue.msgobj.ReportElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by steve on 3/17/16.
 */

/**
 * send the report template file to the jms queue
 * send report element to the middleware
 * middleware will save the report template and store it to amazon s3
 * */
@Component("reportTemplateMessageSender")
public class ReportTemplateMessageSender extends ProducerService<ReportElement> {

    @Autowired
    public ReportTemplateMessageSender(JmsTemplate jmsTemplate, @Qualifier("reportUploaderDestination") Destination destination) {
        super(jmsTemplate);
        this.destination = destination;
        this.log = LogFactory.getLog(ReportTemplateMessageSender.class);
    }

    @Override
    protected MessageCreator getMessageCreator(final ReportElement message) {

        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                StringWriter stringWriter = new StringWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    objectMapper.writeValue(stringWriter, message);
                    TextMessage mapMessage = session.createTextMessage(stringWriter.toString());
                    return mapMessage;
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return null;
                }
            }
        };
    }

}
