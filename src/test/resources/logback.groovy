import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = $/%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M\(%line\) - %msg%n/$
    }
}
appender("FILE", FileAppender) {
    file = "target/selenium.log"
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = $/%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %caller{2} %-5level %logger{36}.%M\(%line\) - %msg%n/$
    }
}
logger("com.sahabt", DEBUG)
root(INFO, ["STDOUT", "FILE"])
