package pertsev.VCS;

import pertsev.VCS.Console.ConsoleManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionControlSystem {
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path DEFAULT_REPOSITORY_DIRECTORY = Paths.get(PROJECT_DIR + "/resources");

    private ConsoleManager consoleManager = new ConsoleManager();
    private Repository repository;
    private Controller controller;

    public VersionControlSystem() throws IOException {
        this.repository = new Repository(DEFAULT_REPOSITORY_DIRECTORY);
        this.controller = new Controller(repository, consoleManager);
    }

    public void launch() throws IOException {
        controller.start();
    }
}