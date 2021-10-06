package com.techprimers.grpc;

import com.techprimers.grpc.service.GreetingServiceBImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpcSpringBootExampleApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(GrpcSpringBootExampleApplication.class, args);

		Server server = ServerBuilder
				.forPort(9092)
				.addService(new GreetingServiceBImpl()).build();

		server.start();

		Thread.sleep(10000);
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
				.usePlaintext()
				.build();

		GreetingServiceAGrpc.GreetingServiceABlockingStub stub
				= GreetingServiceAGrpc.newBlockingStub(channel);

		GreetingResponse helloResponse = stub.greeting(GreetingRequest.newBuilder()
				.setMessage("lfnanfklanfk")
				.build());

		System.out.println(helloResponse.getMessage());


		Thread.sleep(5000);
		channel.shutdown();
		server.awaitTermination();
	}

}
