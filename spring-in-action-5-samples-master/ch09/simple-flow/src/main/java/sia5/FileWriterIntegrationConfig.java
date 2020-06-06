package sia5;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

@Configuration
public class FileWriterIntegrationConfig {

  @Profile("xmlconfig")
  @Configuration
  @ImportResource("classpath:/filewriter-config.xml") // 此/表示为资源路径
  public static class XmlConfiguration {
  }
  
  @Profile("javaconfig")
  @Bean
  @Transformer(inputChannel="textInChannel",
               outputChannel="fileWriterChannel")
  public GenericTransformer<String, String> upperCaseTransformer() {
    return text -> {
      System.out.println("实际使用javaconfig");
      return text.toUpperCase();};
  }

  @Profile("javaconfig")
  @Bean
  @ServiceActivator(inputChannel="fileWriterChannel")
  public FileWritingMessageHandler fileWriter() {
    FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/sia5/files"));
    handler.setExpectReply(false);
    handler.setFileExistsMode(FileExistsMode.APPEND);
    handler.setAppendNewLine(true);
    return handler;
  }
  
  //
  // DSL Configuration
  //
  @Profile("javadsl")
  @Bean
  public IntegrationFlow fileWriterFlow() {
    return IntegrationFlows
        .from(MessageChannels.direct("textInChannel"))
        .<String, String>transform(t -> {
          System.out.println("实际使用dsl");
          return t.toUpperCase();})
        .handle(Files
            .outboundAdapter(new File("/tmp/sia5/files")) // 使用/tmp/将保存到文件系统根目录，使用tmp/保存到该项目的根路径
            .fileExistsMode(FileExistsMode.APPEND)
            .appendNewLine(true))
        .get(); 
  }

}
