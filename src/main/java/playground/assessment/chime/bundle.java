package playground.assessment.chime;

import java.util.HashMap;
import java.util.List;

public record bundle(List<List<String>> matching_word_sets,
                     HashMap<String, String> visited) {}
