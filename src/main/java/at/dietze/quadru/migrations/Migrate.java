package at.dietze.quadru.migrations;

public class Migrate {
    public void runMigrations() {
        new CreatePlayerTable().run();
    }
}
