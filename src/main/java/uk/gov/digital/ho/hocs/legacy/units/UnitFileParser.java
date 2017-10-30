package uk.gov.digital.ho.hocs.legacy.units;

import uk.gov.digital.ho.hocs.legacy.AbstractFilePasrer;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UnitFileParser extends AbstractFilePasrer<CSVUnitLine> {

    @Getter
    private final List<CSVUnitLine> lines;

    public UnitFileParser(MultipartFile file) {
        this.lines = parseUnitTeamsFile(file);
    }

    private static List<CSVUnitLine> parseUnitTeamsFile(MultipartFile file) {
        List<CSVUnitLine> groups = new ArrayList<>();

        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[] lineArray = splitLine(line);
                String unit = lineArray[1].trim();
                String team = lineArray[2].trim();
                groups.add(new CSVUnitLine(unit, team));
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // Remove the heading.
        groups.remove(0);

        return groups;
    }

}