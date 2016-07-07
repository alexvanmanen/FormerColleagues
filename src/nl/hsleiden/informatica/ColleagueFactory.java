package nl.hsleiden.informatica;

import java.util.Arrays;
import java.util.List;

public class ColleagueFactory {
    // Deze class is een Singleton.
    private static ColleagueFactory instance = new ColleagueFactory();

    public static final String ALEX = "Alex";
    public static final String JEROEN = "Jeroen";

    private ColleagueFactory() {}

    public static ColleagueFactory getInstance() {
        return instance;
    }

    public List<String> getColleagueNames() {
        return Arrays.asList(ALEX, JEROEN);
    }

    public Colleague createColleague(String name) {
        if (ALEX.equals(name)) {
            return new Alex();
        } else if (JEROEN.equals(name)) {
            return new Jeroen();
        } else {
            return null;
        }
    }
    

}
