package org.trimou.benchmark;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openjdk.jmh.annotations.Setup;
import org.trimou.Mustache;
import org.trimou.benchmark.data.Food;
import org.trimou.benchmark.data.Item;
import org.trimou.benchmark.data.Origin;
import org.trimou.benchmark.data.Toy;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.locator.ClassPathTemplateLocator;

/**
 *
 * @author Martin Kouba
 */
public class Basic extends BenchmarkBase {

    private Mustache mustache;

    private Map<String, Object> testData;

    @Setup
    public void setup() {
        mustache = MustacheEngineBuilder.newBuilder().addTemplateLocator(new ClassPathTemplateLocator(1, null, "html")).build().getMustache(getMustacheName());

        testData = new HashMap<String, Object>();
        int count = 15;
        List<Item> items = new ArrayList<Item>(count);
        for (int i = 0; i < count; i++) {
            items.add(generateItem(i));
        }
        testData.put("items", items);
        testData.put("name", "Foo");
    }

    protected String getMustacheName() {
        return "templates/basic";
    }

    @Override
    protected Mustache getMustache() {
        return mustache;
    }

    @Override
    protected Object getTestData() {
        return testData;
    }

    private Item generateItem(int idx) {
        if (idx % 2 == 0) {
            Toy toy = new Toy();
            toy.setName("" + idx);
            toy.setPrice(new BigDecimal("5000"));
            toy.setMinAge(10);
            return toy;
        } else {
            Food food = new Food();
            food.setName("" + idx);
            food.setPrice(new BigDecimal("2000"));
            food.setOrigin(new Origin("C10"));
            return food;
        }
    }

}
