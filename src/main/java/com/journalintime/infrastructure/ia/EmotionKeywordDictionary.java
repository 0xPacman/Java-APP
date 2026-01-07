package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;

import java.util.*;

/**
 * Dictionnaire de mots-clés pour la classification des émotions.
 */
public class EmotionKeywordDictionary {

    private static final Map<EmotionType, List<String>> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put(EmotionType.JOIE, Arrays.asList(
            "heureux", "joie", "content", "ravi", "excité", "enthousiaste", 
            "sourire", "bonheur", "génial", "super", "excellent", "merveilleux"
        ));

        KEYWORDS.put(EmotionType.TRISTESSE, Arrays.asList(
            "triste", "malheureux", "déprimé", "mélancolique", "chagrin",
            "peine", "désolé", "abattu", "morose", "pessimiste"
        ));

        KEYWORDS.put(EmotionType.COLERE, Arrays.asList(
            "colère", "furieux", "énervé", "irrité", "frustré", "rage",
            "fâché", "agacé", "exaspéré", "mécontent"
        ));

        KEYWORDS.put(EmotionType.PEUR, Arrays.asList(
            "peur", "effrayé", "terrifié", "angoisse", "crainte", "inquiet",
            "anxieux", "nerveux", "stressé", "paniqué", "préoccupé"
        ));

        KEYWORDS.put(EmotionType.ANXIETE, Arrays.asList(
            "anxieux", "angoisse", "stress", "inquiet", "nerveux", "tendu",
            "préoccupé", "agité", "tourmenté", "crispé"
        ));

        KEYWORDS.put(EmotionType.SERENITE, Arrays.asList(
            "serein", "calme", "paisible", "tranquille", "apaisé", "détendu",
            "zen", "relaxé", "pacifique", "repos"
        ));

        KEYWORDS.put(EmotionType.FRUSTRATION, Arrays.asList(
            "frustré", "contrarié", "déçu", "insatisfait", "mécontent",
            "bloqué", "impuissant", "limité"
        ));

        KEYWORDS.put(EmotionType.EXCITATION, Arrays.asList(
            "excité", "enthousiaste", "motivé", "énergique", "dynamique",
            "vivant", "stimulé", "emballé"
        ));

        KEYWORDS.put(EmotionType.SURPRISE, Arrays.asList(
            "surpris", "étonné", "stupéfait", "choqué", "abasourdi",
            "déconcerté", "inattendu"
        ));

        KEYWORDS.put(EmotionType.DEGOUT, Arrays.asList(
            "dégoût", "répulsion", "aversion", "écœuré", "repoussé",
            "répugnant", "insupportable"
        ));
    }

    public static Map<EmotionType, List<String>> getKeywords() {
        return Collections.unmodifiableMap(KEYWORDS);
    }

    public static List<String> getKeywordsForEmotion(EmotionType emotionType) {
        return KEYWORDS.getOrDefault(emotionType, Collections.emptyList());
    }
}
