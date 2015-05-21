package org.trimou.benchmark;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.trimou.Mustache;
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
@Fork(5)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public abstract class BenchmarkBase {

    private Mustache mustache;

    @Benchmark
    public String render() {
        return mustache.render(getTestData());
    }

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
    }

    protected abstract Object getTestData();

    protected abstract String getMustacheName();

    protected void customizeMustacheEngineBuilder(MustacheEngineBuilder builder) {
        // Noop
    }

}
