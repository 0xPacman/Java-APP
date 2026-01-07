package com.journalintime.infrastructure.ia;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processeur de texte pour normalisation et extraction de features.
 */
public class TextProcessor {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
        "le", "la", "les", "un", "une", "des", "de", "du", "à", "au", "aux",
        "ce", "cet", "cette", "ces", "mon", "ma", "mes", "ton", "ta", "tes",
        "son", "sa", "ses", "notre", "nos", "votre", "vos", "leur", "leurs",
        "qui", "que", "quoi", "dont", "où", "et", "ou", "mais", "donc", "car",
        "ni", "ne", "pas", "plus", "moins", "très", "trop", "assez", "bien",
        "mal", "peu", "beaucoup", "tout", "toute", "tous", "toutes"
    ));

    /**
     * Normalise le texte: minuscules, suppression des accents et ponctuation.
     */
    public static String normalize(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        
        // Minuscules
        String normalized = text.toLowerCase();
        
        // Suppression des accents
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        
        // Suppression de la ponctuation
        normalized = normalized.replaceAll("[^a-z0-9\\s]", " ");
        
        // Normalisation des espaces
        normalized = normalized.replaceAll("\\s+", " ").trim();
        
        return normalized;
    }

    /**
     * Tokenize le texte en mots.
     */
    public static List<String> tokenize(String text) {
        String normalized = normalize(text);
        return Arrays.stream(normalized.split("\\s+"))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Retire les stop words du texte.
     */
    public static List<String> removeStopWords(List<String> tokens) {
        return tokens.stream()
                .filter(token -> !STOP_WORDS.contains(token))
                .collect(Collectors.toList());
    }

    /**
     * Calcule la fréquence des mots.
     */
    public static Map<String, Integer> calculateWordFrequency(List<String> tokens) {
        Map<String, Integer> frequency = new HashMap<>();
        for (String token : tokens) {
            frequency.put(token, frequency.getOrDefault(token, 0) + 1);
        }
        return frequency;
    }

    /**
     * Extrait les n-grammes d'un texte.
     */
    public static List<String> extractNGrams(List<String> tokens, int n) {
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i <= tokens.size() - n; i++) {
            StringBuilder ngram = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (j > 0) ngram.append(" ");
                ngram.append(tokens.get(i + j));
            }
            ngrams.add(ngram.toString());
        }
        return ngrams;
    }
}
