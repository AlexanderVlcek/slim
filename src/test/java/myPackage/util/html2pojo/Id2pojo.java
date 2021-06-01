package myPackage.util.html2pojo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.base.CaseFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Slf4j
public class Id2pojo {
    private static final Logger log = LogManager.getLogger(Id2pojo.class);

    public static void main(String[] args) {
        String dirPath = new File("").getAbsolutePath();
        String  sourcePath = "/src/test/java/";
        String packagePath = "myPackage/util/html2pojo/";
        String path = dirPath +sourcePath+ packagePath;
        List<String> inputFile = readFile2StringLines(path + "myIds.html");
        List<String> ids = extractIds(inputFile);
        List<String> by = createByStrings(ids);
        linesToPojo(dirPath, "Breakdown", by);
    }

    private static void linesToPojo(String dirPath, String className, List<String> linesWithBy) {
        String path = dirPath + className + ".java";
        String header =
                "import org.openqa.selenium.By;\n" +
                "\n" +
                "\n" +
                "public class " + className + " {" +
                "\n";

        String footer =
                "\n" +
                        "    private " + className + "() {\n" +
                        "    }\n" +
                        "}";

        File file = new File(path);
        if (!file.exists()) {
            if (file.getParentFile().mkdirs()) {
                log.info("File created:" + file.getParentFile().getPath());
            }
            try {
                if (file.createNewFile()) {
                    log.info("File created:" + file.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(fw)) {
            writer.append(header);
            for (String line : linesWithBy) {
                writer.append(line);
                writer.newLine();
            }
            writer.append(footer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static List<String> createByStrings(List<String> ids) {
        String STRING_FORMAT = "public static final By %s_ID = By.id(\"%s\");";
        List<String> linesWithBy = new ArrayList<>();
        ids.forEach(
                id -> {
                    String formattedId = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, id);
                    formattedId = formattedId.replace("-","_");
                    linesWithBy.add(String.format(STRING_FORMAT, formattedId, id));
                }
        );
        return linesWithBy;
    }

    private static List<String> extractIds(List<String> lines) {
        List<String> listIds = new ArrayList<>();
        String htmlRegex = "(\\sid=\")(.*?)(\")";
        Pattern r = Pattern.compile(htmlRegex);
        lines.forEach(line -> {
            Matcher m = r.matcher(line);
            while (m.find()) {
                listIds.add(m.group(2));
            }
        });
        return listIds.stream().distinct().collect(Collectors.toList());
    }

    private static List<String> readFile2StringLines(String path) {
        try {
            File file = new File(path);
            System.out.println("Reading files");
            return FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("File not read.");
    }
}

