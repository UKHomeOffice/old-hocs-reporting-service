package com.sls.listService.legacy.topics;

import com.sls.listService.legacy.AbstractFilePasrer;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class UKVIFileParser extends AbstractFilePasrer<CSVTopicLine> {

    @Getter
    private final List<CSVTopicLine> lines;

    public UKVIFileParser(MultipartFile file) {
        this.lines = parseUKVIFile(file);

    }

    private static List<CSVTopicLine> parseUKVIFile(MultipartFile file) {
        List<CSVTopicLine> result = new ArrayList<>();

        List<String> lines = new ArrayList<>();
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // Remove the heading.
        lines.remove(0);

        Map<String, String> parentTopics = lines.stream().collect(Collectors.toMap(l -> getColumn(l, 0), l -> getColumn(l, 1)));

        List<String> erListOne = lines.stream().map(l -> getColumn(l, 3)).filter(l -> !l.isEmpty()).collect(Collectors.toList());
        List<String> erListTwo = lines.stream().map(l -> getColumn(l, 4)).filter(l -> !l.isEmpty()).collect(Collectors.toList());
        List<String> erListThree = lines.stream().map(l -> getColumn(l, 5)).filter(l -> !l.isEmpty()).collect(Collectors.toList());
        List<String> erListFour = lines.stream().map(l -> getColumn(l, 6)).filter(l -> !l.isEmpty()).collect(Collectors.toList());
        List<String> erListFive = lines.stream().map(l -> getColumn(l, 7)).filter(l -> !l.isEmpty()).collect(Collectors.toList());

        for (Map.Entry<String, String> entity : parentTopics.entrySet()) {
            String erListReference = entity.getValue().trim();

            List<String> listToAdd = new ArrayList<>();
            switch (erListReference) {
                case "ER List 1":
                    listToAdd = erListOne;
                    break;
                case "ER List 2":
                    listToAdd = erListTwo;
                    break;
                case "ER List 3":
                    listToAdd = erListThree;
                    break;
                case "ER List 4":
                    listToAdd = erListFour;
                    break;
                case "ER List 5":
                    listToAdd = erListFive;
                    break;
            }

            for (String subTopic : listToAdd) {
                result.add(new CSVTopicLine(entity.getKey(), subTopic, "GROUP_UKVI", ""));
            }

        }

        return result;
    }

    private static String getColumn(String line, int column) {
        return splitLine(line)[column];
    }

}