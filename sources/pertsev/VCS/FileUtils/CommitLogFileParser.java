package pertsev.VCS.FileUtils;

import pertsev.VCS.VCSUtils.Commit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CommitLogFileParser {
    private final String COMMIT_SEPARATOR = "---";
    private Path commitLogFile;

    public CommitLogFileParser(Path commitLofFile) {
        this.commitLogFile = commitLofFile;
    }

    public Queue<Commit> readCommitQueue() throws IOException {
        String fileText = Files.readAllLines(commitLogFile).toString();
        Queue<Commit> commits = new LinkedList<>();
        for (String commitText : fileText.split(COMMIT_SEPARATOR)) {
            String[] lines = commitText.split("\n");
            String commitName = lines[0].split(" ")[0].strip();
            String editedFile = lines[1].strip();

            Map<Integer, String> editedLines = new HashMap<>();
            for (int indexLine = 2; i < lines.length; i++) {
                editedLines.put(i - 2, )
            }
        }
    }
}
