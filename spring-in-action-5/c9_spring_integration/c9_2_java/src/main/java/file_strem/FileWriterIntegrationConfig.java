package file_strem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransformer() {
        System.out.println("创建转换器");
        return text -> {
            System.out.println("开始转换大小写");
            return text.toUpperCase();
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        System.out.println("创建服务激活器");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("./"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

    @Bean
    public MessageChannel textInChannel() {
        System.out.println("创建通道textInChannel");
        return new DirectChannel();
    }

    @Bean
    public MessageChannel fileWriterChannel() {
        System.out.println("创建通道fileWriterChannel");
        return new DirectChannel();
    }

}
