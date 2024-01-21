package playground.assessment.bluecrew.timekeeper;

import java.util.HashMap;

public record TimeKeeper(HashMap<String, TimeUser> users) { }
