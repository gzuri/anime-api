package com.goranzuri.anime.api.syncservice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import org.apache.commons.cli.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * Created by gzuri on 22/10/2017.
 */
public class Main {
    private static final String DIRECTORY_ARGUMENT = "dir";
    private static final String STORAGE_ARGUMENT = "storage";
    private static final String URL_ARGUMENT = "url";
    private static final String BASIC_AUTH = "auth";

    private static Options prepareCommandLineOptions() {
        Option option_directory = OptionBuilder.withArgName(DIRECTORY_ARGUMENT).hasArg().withDescription("source directory to search").create(DIRECTORY_ARGUMENT);
        Option option_storage = OptionBuilder.withArgName(STORAGE_ARGUMENT).hasArg().withDescription("source directory to search").create(STORAGE_ARGUMENT);
        Option option_url = OptionBuilder.withArgName(URL_ARGUMENT).hasArg().withDescription("source directory to search").create(URL_ARGUMENT);
        Option option_auth = OptionBuilder.withArgName(BASIC_AUTH).hasArg().withDescription("basic auth on server").create(BASIC_AUTH);

        Options options = new Options();
        options.addOption(option_directory);
        options.addOption(option_storage);
        options.addOption(option_url);
        options.addOption(option_auth);

        return options;
    }

    public static void main(String... args) throws ParseException, IOException {
        CommandLineParser parser = new GnuParser();
        CommandLine commandLine;
        Options options = Main.prepareCommandLineOptions();
        SyncDriveReq syncDriveReq = new SyncDriveReq();
        List<String> folderList = new ArrayList<>();

        commandLine = parser.parse(options, args);

        if (args.length < 2 || !commandLine.hasOption(DIRECTORY_ARGUMENT) || !commandLine.hasOption(STORAGE_ARGUMENT)){
            // Main.printHelpPage(options);
            return;
        }


        File parentFolder = new File(commandLine.getOptionValue(DIRECTORY_ARGUMENT).toString());
        File[] folders = parentFolder.listFiles(File::isDirectory);
        Arrays.sort(folders);

        for (File folder: folders){
            folderList.add(folder.getName());
        }

        syncDriveReq.setStorage(commandLine.getOptionValue(STORAGE_ARGUMENT).toString());
        syncDriveReq.setNamesOnDisk(folderList);

        Gson gson = new Gson();
        String json = gson.toJson(syncDriveReq);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(commandLine.getOptionValue(URL_ARGUMENT).toString());
        StringEntity params =new StringEntity(json);
        request.addHeader("content-type", "application/json");
        if (commandLine.hasOption(BASIC_AUTH)){
            request.setHeader("Authorization", "Basic " + commandLine.getOptionValue(BASIC_AUTH).toString());
        }
        request.setEntity(params);
        httpClient.execute(request);
    }
}
