package playground.assessment.bluecrew.timekeeper;

import java.util.Map;

public record Action(Actions.COMMAND state, Map<String, Object> data) { }
