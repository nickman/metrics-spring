/**
 * 
 */
package com.ryantenney.metrics.spring.reporter;

import java.util.concurrent.TimeUnit;

import com.heliosapm.opentsdb.client.opentsdb.OpenTSDBReporter;

/**
 * @author nwhitehead
 *
 */
public class OpenTSDBReporterFactoryBean extends AbstractReporterFactoryBean<OpenTSDBReporter> {
	// Required
	public static final String PERIOD = "period";

	// Optional
	
	public static final String OUTPUT_REF = "output-ref";
	public static final String LOCALE = "locale";
	public static final String TIMEZONE = "timezone";
	public static final String DURATION_UNIT = "duration-unit";
	public static final String RATE_UNIT = "rate-unit";


	@Override
	public Class<? extends OpenTSDBReporter> getObjectType() {
		return OpenTSDBReporter.class;
	}

	@Override
	protected OpenTSDBReporter createInstance() throws Exception {
		final OpenTSDBReporter.Builder reporter = OpenTSDBReporter.forRegistry(getMetricRegistry());

		if (hasProperty(DURATION_UNIT)) {
			reporter.convertDurationsTo(getProperty(DURATION_UNIT, TimeUnit.class));
		}

		if (hasProperty(RATE_UNIT)) {
			reporter.convertRatesTo(getProperty(RATE_UNIT, TimeUnit.class));
		}

		reporter.filter(getMetricFilter());


//		if (hasProperty(OUTPUT_REF)) {
//			reporter.outputTo(getPropertyRef(OUTPUT_REF, PrintStream.class));
//		}
//
//		if (hasProperty(LOCALE)) {
//			reporter.formattedFor(parseLocale(getProperty(LOCALE)));
//		}
//
//		if (hasProperty(TIMEZONE)) {
//			reporter.formattedFor(TimeZone.getTimeZone(getProperty(TIMEZONE)));
//		}

		return reporter.build();
	}

}
