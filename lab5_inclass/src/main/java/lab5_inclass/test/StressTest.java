package lab5_inclass.test;

import lab5_inclass.model.Service;
import lab5_inclass.model.messages.Message;
import lab5_inclass.model.messages.MessageDataModel;
import lab5_inclass.model.messages.RecoveryMessage;
import lab5_inclass.model.util.Utils;
import lab5_inclass.model.messages.RecoveryMessage;
import lab5_inclass.model.Service;
import lab5_inclass.model.util.Utils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja <ovidiu.maja@tora.com>
 * @version Dec 08, 2017
 */
public class StressTest {
    public static void main(String[] args) throws Exception {
        Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("%d{MM-dd HH:mm:ss,SSS} [%t] %5p: %m%n")));
        Logger.getRootLogger().setLevel(Level.DEBUG);

        final RecoveryMessage recoveryMessage = new RecoveryMessage("GROUP1~1~ADD~data1");
        final RecoveryMessage recoveryMessage2 = new RecoveryMessage("GROUP1~1~ADD~data1");

        Service service = new Service(Arrays.asList("GROUP1", "GROUP2"), 20);
        service.process(recoveryMessage);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            IntStream.rangeClosed(0, 10_000)
                    .mapToObj(count -> new RecoveryMessage("GROUP1~" + count + "~ADD~data2"))
                    .forEach(service::process);
            Logger.getRootLogger().info("Recovery done");
        });

        service.process(recoveryMessage2);
        service.start();

        executorService.shutdown();
        Utils.simulateWork(TimeUnit.SECONDS.toMillis(2));
        service.close();
    }
}
