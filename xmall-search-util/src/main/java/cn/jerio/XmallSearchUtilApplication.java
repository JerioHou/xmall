package cn.jerio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.jerio.mapper")
public class XmallSearchUtilApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmallSearchUtilApplication.class, args);
	}
}
