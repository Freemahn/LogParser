package com.freemahn.logparser;

import com.freemahn.logparser.entity.BadIp;
import com.freemahn.logparser.entity.Log;
import com.freemahn.logparser.repository.BadIpRepository;
import com.freemahn.logparser.repository.LogRepository;
import org.apache.commons.cli.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pavel Gordon
 */
@Component
public class Parser implements CommandLineRunner {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long threshold;
    @Autowired
    LogRepository logRepository;

    @Autowired
    BadIpRepository badIpRepository;
    private String accesslogPath;

    public void run(String... args) throws IOException, URISyntaxException {
        System.out.println("Started");
        parseOptions(args);

        System.out.print("Parsing file...");
        Stream<String> lines = readFile();
        List<Log> logs = lines.map(this::parseLine).collect(Collectors.toList());
        logRepository.saveAll(logs);
        System.out.println("finished");


        List<BadIp> badIps = logRepository.findIpsBetweenDatesWithThreshold(this.startDate, endDate, this.threshold);
        badIps.forEach(badIp -> {
            System.out.println(badIp.getIp());
            badIp.setCommentary("There has been made more than " + threshold + " requests from this ip between " + startDate + " and " + endDate);

        });
        badIpRepository.saveAll(badIps);
    }

    private void parseOptions(String... args) {
        Options options = new Options();

        Option accessLogOption = new Option("a", "accesslog", true, "path to access log");
        accessLogOption.setRequired(true);
        options.addOption(accessLogOption);

        Option startDateOption = new Option("s", "startDate", true, "start date");
        startDateOption.setRequired(true);
        options.addOption(startDateOption);

        Option durationOption = new Option("d", "duration", true, "duration of time period");
        durationOption.setRequired(true);
        options.addOption(durationOption);

        Option thresholdOption = new Option("t", "threshold", true, "max amount requests with same IP");
        thresholdOption.setRequired(true);
        options.addOption(thresholdOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar LogParser-0.0.1-SNAPSHOT.jar", options);

            System.exit(1);
            return;
        }

        String accesslogPath = cmd.getOptionValue("accesslog");
        String startDate = cmd.getOptionValue("startDate");
        String duration = cmd.getOptionValue("duration");
        String threshold = cmd.getOptionValue("threshold");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        this.startDate = LocalDateTime.parse(startDate, dateTimeFormatter);
        switch (duration) {
            case "hourly": {
                this.endDate = this.startDate.plusHours(1);
            }
            case "daily": {
                this.endDate = this.startDate.plusDays(1);
            }
        }
        this.threshold = Long.parseLong(threshold);
        this.accesslogPath = accesslogPath;
    }

    private Stream<String> readFile() throws IOException {
        Path path = Paths.get(accesslogPath);
        if (!Files.exists(path)) {
            System.exit(1);
        }
        return Files.lines(path);

    }

    private Log parseLine(String line) {
        String[] splitted = line.split("\\|");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime = LocalDateTime.parse(splitted[0], dateTimeFormatter);
        String ip = splitted[1];
        String request = splitted[2];
        String code = splitted[3];
        String userAgent = splitted[4];
        return Log.newBuilder()
                .dateTime(localDateTime)
                .ip(ip)
                .request(request)
                .code(Integer.parseInt(code))
                .userAgent(userAgent)
                .build();

    }

}