package playground.assessment.bluecrew;

import java.util.Map;

public record Action(Actions.COMMAND state, Map<String, Object> data) { }
