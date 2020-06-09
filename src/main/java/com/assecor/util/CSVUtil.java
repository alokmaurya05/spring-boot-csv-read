package com.assecor.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

public class CSVUtil
{
    private static final String DELIMITER_COMMA = ",";

    public static List<List<String>> readFile(String file) throws Exception
    {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            String[] improperValue = new String[4];
            while ((line = br.readLine()) != null)
            {
                if (isEmpty(line))
                {
                    continue;
                }
                String[] values = line.split(DELIMITER_COMMA);
                if (values.length != 4)
                {
                	
                	//records.addAll(extractRecordsFromSingleLine(values));
                	if(values[1].matches("[0-9]+") && values[1].length() == 1)
                	{
                		improperValue[2] = values[0];
                		improperValue[3] = values[1];
                		records.add(asList(improperValue).stream()
                                .map(String::trim)
                                .collect(toList()));
                		
                	}
                	else {
                	improperValue[0] = values[0];
                	improperValue[1] = values[1];
                	}

                }
                else
                {
                    records.add(asList(values).stream()
                            .map(String::trim)
                            .collect(toList()));
                }
            }
        }

        return records;
    }

}
