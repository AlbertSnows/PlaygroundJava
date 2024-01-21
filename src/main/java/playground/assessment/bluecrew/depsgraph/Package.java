package playground.assessment.bluecrew.depsgraph;

import java.util.List;

public record Package(String name, List<Package> dependencies) { }
