package com.starter.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.starter.model.OrderTransaction;

@Component
public class OrderTransactionReceiver {


	  @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
	  public void receiveMessage(OrderTransaction transaction) {
		  System.out.println("Received <" + transaction + ">" + transaction.getNome());
	  }
	
}
