package com.milan.fuse.camel.route;
import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.cxf.message.MessageContentsList;
//import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import java.util.List;

import javax.xml.bind.JAXBContext;

import com.milan.fuse.cxf.vo.PersonVO;

public class ConsumerRoute extends RouteBuilder{
	@Override
	public void configure() throws Exception
	{

		errorHandler(deadLetterChannel("activemq:queue:deadMsgQueue")
			    .maximumRedeliveries(5).redeliveryDelay(5000));
		from("activemq:queue:myQueue").routeId("consumerRoute").autoStartup(true).process(new Processor(){
			@Override
			public void process(Exchange exchange)
			{
				MessageContentsList mcl = (MessageContentsList)exchange.getIn().getBody();
				List<PersonVO> personList = (List<PersonVO>)mcl.get(0);
				exchange.getOut().setBody(personList);
				exchange.getOut().setHeader("listSize",personList.size());
				exchange.getOut().setHeader("Content-Type","application/json");
				exchange.getOut().setHeader("CamelHttpMethod","PUT");
				exchange.getOut().setHeader("CamelCxfRsUsingHttpAPI","False");
				exchange.getOut().setHeader(CxfConstants.OPERATION_NAME,"createPerson");
			}
		}).log("${body}").split(body()).to("cxfrs:bean:personRSClient");
	}

}
