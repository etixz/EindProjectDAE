package dae.mob123.fragments.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class TextNormalizer {

    public TextNormalizer() {
    }

    public static String removeAccents(String text) {
        return text == null ? null : Normalizer.normalize(text, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
