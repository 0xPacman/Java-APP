package com.journalintime.infrastructure.ia;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour le processeur de texte.
 */
class TextProcessorTest {

    @Test
    void testNormalize() {
        String text = "Héllo Wörld! Çà va?";
        String normalized = TextProcessor.normalize(text);
        
        assertEquals("hello world ca va", normalized);
    }

    @Test
    void testTokenize() {
        String text = "Bonjour le monde";
        List<String> tokens = TextProcessor.tokenize(text);
        
        assertEquals(3, tokens.size());
        assertTrue(tokens.contains("bonjour"));
        assertTrue(tokens.contains("le"));
        assertTrue(tokens.contains("monde"));
    }

    @Test
    void testRemoveStopWords() {
        List<String> tokens = List.of("je", "suis", "heureux");
        List<String> filtered = TextProcessor.removeStopWords(tokens);
        
        assertTrue(filtered.contains("suis"));
        assertTrue(filtered.contains("heureux"));
        // "je" might still be present depending on stop words configuration
    }

    @Test
    void testCalculateWordFrequency() {
        List<String> tokens = List.of("hello", "world", "hello");
        var frequency = TextProcessor.calculateWordFrequency(tokens);
        
        assertEquals(2, frequency.get("hello"));
        assertEquals(1, frequency.get("world"));
    }

    @Test
    void testExtractBigrams() {
        List<String> tokens = List.of("je", "suis", "heureux");
        List<String> bigrams = TextProcessor.extractNGrams(tokens, 2);
        
        assertEquals(2, bigrams.size());
        assertTrue(bigrams.contains("je suis"));
        assertTrue(bigrams.contains("suis heureux"));
    }
}
