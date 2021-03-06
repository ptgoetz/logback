import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.classic.net.SMTPAppender
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy


import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO



appender("RootFileAppender", RollingFileAppender) {
  file = "project"
  append = true
  filter(ThresholdFilter) {
    level = INFO
  }
  rollingPolicy(FixedWindowRollingPolicy) {
    fileNamePattern = "project_log.%i"
    maxIndex = 1
  }
  triggeringPolicy(SizeBasedTriggeringPolicy) {
    maxFileSize = 1000000
  }

  layout(PatternLayout) {
    pattern = "%d{yyyy-MM-dd HH:mm:ss}, %p, %c, %t, %ex, %F, %L, %C{1}, %M %m%n"
  }
}

appender("RootConsoleAppender", ConsoleAppender) {
  filter(ThresholdFilter) {
    level = INFO
  }

  layout(PatternLayout) {
    pattern = "%d{yyyy-MM-dd HH:mm:ss}, %p, %c, %t %m%n"
  }
}

appender("RootEmailAppender", SMTPAppender) {
  filter(ThresholdFilter) {
    level = ERROR
  }
  bufferSize = 10
  SMTPHost = "smtp.hostaddress"
  to = "logback.user@xyz.com"
  from = " logback.user@xyz.com "
  username = "user"
  password = "password"
  subject = "Logback Error"

  layout(PatternLayout) {
    pattern = "%d{yyyy-MM-dd HH:mm:ss}, %p, %c, %t, %ex, %F, %L, %C{1}, %M %m%n"
  }

}

root(INFO, ["RootFileAppender", "RootConsoleAppender", "RootEmailAppender"])