package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import tests.model.Goods;

import java.io.InputStreamReader;
import java.io.Reader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckJSONTest {
    private final ClassLoader cl = CheckJSONTest.class.getClassLoader();
    private static final ObjectMapper json = new ObjectMapper();

    @Test
    void jsonFileParsingImprovedTest() throws Exception {

        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("goods.json"))
        ) {
            Goods goods = json.readValue(reader, Goods.class);
            assertThat(goods.getName())
                    .isEqualTo("ДЖЕМПЕР ЖЕНСКИЙ");
            assertThat(goods.getPrice().getTypeName())
                    .isEqualTo("Розничная цена");
            assertThat(goods.getPrice().getPrice())
                    .isEqualTo(999.00);
        }
    }
}
