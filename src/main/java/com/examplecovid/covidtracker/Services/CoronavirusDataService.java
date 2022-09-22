package com.examplecovid.covidtracker.Services;

import ch.qos.logback.core.net.server.Client;
import com.examplecovid.covidtracker.Services.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class CoronavirusDataService {


    private final String  Virus_Data_Url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv" ;

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    private List<LocationStats> allStats = new ArrayList<>();
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest  request  = HttpRequest.newBuilder()
                .uri(URI.create(Virus_Data_Url))
                .build();
        HttpResponse<String> httpResponse  = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader cvsReader = new StringReader(httpResponse.body()) ;
        Iterable<CSVRecord> records  = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(cvsReader);
        for(CSVRecord record : records)
        {
            LocationStats  locatioStat  = new LocationStats();
           locatioStat.setState(record.get("Province/State"));
           locatioStat.setCountry(record.get("Country/Region"));
           int latestCases = Integer.parseInt(record.get(record.size()-1));
           int previuosDays = Integer.parseInt(record.get(record.size()-2));
            locatioStat.setLatestTotalCases(latestCases);
            locatioStat.setDiffPreviousDays(latestCases-previuosDays);
          System.out.println(locatioStat);
          newStats.add(locatioStat);

        }
        this.allStats = newStats;
    }
}
