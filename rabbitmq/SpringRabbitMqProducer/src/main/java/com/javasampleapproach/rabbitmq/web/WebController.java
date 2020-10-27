package com.javasampleapproach.rabbitmq.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.rabbitmq.producer.Producer;

@RestController
public class WebController {
	
	@Autowired
	Producer producer;
	
	@RequestMapping("/send")
	public String sendMsg(@RequestParam("msg")String msg){
		producer.produceMsg(msg);
		return "Done";
	}
}
