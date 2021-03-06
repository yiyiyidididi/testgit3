package springcloud.helloworld.eureka.client;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController

public class EurekaClientApplication {

    public static void main(String[] args) {
    	System.out.println(122);
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @Value("${server.port}")
    String port;
    @RequestMapping("/home")
    public String home() {
    	
    	System.out.println(123);
    
    	System.out.println(124);

    	System.out.println(122);



        return "hello world from port " + port;     
    }

}
