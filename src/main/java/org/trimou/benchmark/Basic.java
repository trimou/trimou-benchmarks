package org.trimou.benchmark;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openjdk.jmh.annotations.Setup;
import org.trimou.Mustache;
import org.trimou.benchmark.data.Item;
import org.trimou.engine.MustacheEngineBuilder;
import org.trimou.engine.config.Configuration;
import org.trimou.engine.config.ConfigurationKey;
import org.trimou.engine.locale.LocaleSupport;
import org.trimou.engine.locator.ClassPathTemplateLocator;
import org.trimou.handlebars.HelpersBuilder;
import org.trimou.handlebars.i18n.DateTimeFormatHelper;

/**
 *
 * @author Martin Kouba
 */
public class Basic extends BenchmarkBase {

    private Mustache mustache;

    private Map<String, Object> testData;

    @Setup
    public void setup() {
        MustacheEngineBuilder builder = MustacheEngineBuilder.newBuilder();
        builder.addTemplateLocator(new ClassPathTemplateLocator(1, null, "html"));
        builder.setLocaleSupport(new LocaleSupport() {
            @Override
            public void init(Configuration configuration) {
            }

            @Override
            public Set<ConfigurationKey> getConfigurationKeys() {
                return Collections.emptySet();
            }

            @Override
            public Locale getCurrentLocale() {
                return Locale.ENGLISH;
            }
        });
        builder.registerHelpers(HelpersBuilder.extra().build()).registerHelper("dateTime", new DateTimeFormatHelper());
        customizeMustacheEngineBuilder(builder);
        mustache = builder.build().getMustache(getMustacheName());

        testData = new HashMap<String, Object>();
        int count = 15;
        List<Item> items = new ArrayList<Item>(count);
        for (int i = 0; i < count; i++) {
            items.add(generateItem(i));
        }
        testData.put("items", items);
        testData.put("name", "Foo");
    }

    protected void customizeMustacheEngineBuilder(MustacheEngineBuilder builder) {
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

    protected Item generateItem(int idx) {
        Item item = new Item();
        item.setId(Long.valueOf(idx * 10));
        item.setName("" + idx);
        item.setPrice(new BigDecimal(idx * 1000));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        item.setCreated(cal.getTime());
        item.setDescription(String.format("Item %s with price %s (created at %s)", item.getId(), item.getPrice(), item.getCreated()));
        return item;
    }

}
