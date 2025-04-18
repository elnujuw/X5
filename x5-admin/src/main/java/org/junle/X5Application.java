package org.junle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author elnujuw
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class X5Application
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(X5Application.class, args);
        System.out.println("X5启动成功......\n" +
                "   _  ______\n" +
                "  | |/_/ __/\n" +
                " _>  </__ \\ \n" +
                "/_/|_/____/ \n" +
                "            ");
    }
}
