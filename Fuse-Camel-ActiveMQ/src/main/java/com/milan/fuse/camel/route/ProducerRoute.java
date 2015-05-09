package com.milan.fuse.camel.route;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;

import java.util.List;


import com.milan.fuse.cxf.vo.PersonVO;

public class ProducerRoute extends RouteBuilder{
	@Override
	public void configure() throws Exception
	{
		onException(Exception.class).handled(true).process(new Processor(){
			@Override
			public void process(Exchange exchange){
				Boolean returnValue = new Boolean(false);
				exchange.getOut().setBody(returnValue);
			}
		}).stop();
		from("cxf:bean:personSoapService").routeId("producerRoute").inOnly("activemq:queue:myQueue").process(
				new Processor(){
					public void process(Exchange exchange)
					{
						Object[] args =  exchange.getIn().getBody(Object[].class);
						List<PersonVO> personList = (List<PersonVO>)args[0];			
//						exchange.getOut().setBody(personList);
						exchange.getOut().setHeader("listSize",personList.size());
						Boolean returnValue = new Boolean(true);
						exchange.getOut().setBody(returnValue);
					}
				});//.log("${body}").to("activemq:queue:myQueue");
	}

}
