package com.journalintime.infrastructure.ia;

import com.journalintime.domain.enums.EmotionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour le classificateur d'émotions.
 */
class EmotionClassifierTest {

    private final EmotionClassifier classifier = new EmotionClassifier();

    @Test
    void testClassifyJoyfulText() {
        String text = "Je suis très heureux aujourd'hui. C'est une journée merveilleuse et je me sens ravi.";
        ClassificationResult result = classifier.classify(text);
        
        assertEquals(EmotionType.JOIE, result.getDominantEmotion());
        assertTrue(result.getIntensity() > 0.3);
    }

    @Test
    void testClassifySadText() {
        String text = "Je me sens triste et déprimé. J'ai beaucoup de chagrin aujourd'hui.";
        ClassificationResult result = classifier.classify(text);
        
        assertEquals(EmotionType.TRISTESSE, result.getDominantEmotion());
        assertTrue(result.getIntensity() > 0.2);
    }

    @Test
    void testClassifyAnxiousText() {
        String text = "Je suis très anxieux et stressé. Je me sens nerveux et inquiet.";
        ClassificationResult result = classifier.classify(text);
        
        // Either ANXIETE or PEUR is acceptable since keywords overlap
        assertTrue(result.getDominantEmotion() == EmotionType.ANXIETE || 
                  result.getDominantEmotion() == EmotionType.PEUR);
        assertTrue(result.getIntensity() > 0.2);
    }

    @Test
    void testClassifyEmptyText() {
        String text = "";
        ClassificationResult result = classifier.classify(text);
        
        assertNotNull(result);
        assertEquals(0.0, result.getIntensity());
    }

    @Test
    void testClassifyNullText() {
        ClassificationResult result = classifier.classify(null);
        
        assertNotNull(result);
        assertEquals(0.0, result.getIntensity());
    }
}
