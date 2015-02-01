/**
 * 
 */
package com.ryantenney.metrics.spring.reporter;

import static com.ryantenney.metrics.spring.reporter.AbstractReporterFactoryBean.FILTER_PATTERN;
import static com.ryantenney.metrics.spring.reporter.AbstractReporterFactoryBean.FILTER_REF;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.CLOCK_REF;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.DURATION_UNIT;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.LOCALE;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.OUTPUT_REF;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.PERIOD;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.RATE_UNIT;
import static com.ryantenney.metrics.spring.reporter.ConsoleReporterFactoryBean.TIMEZONE;

import com.ryantenney.metrics.spring.reporter.AbstractReporterElementParser.ValidationContext;

/**
 * @author nwhitehead
 *
 */
public class OpenTSDBReporterElementParser extends AbstractReporterElementParser {


	@Override
	public String getType() {
		return "opentsdb";
	}
	
	@Override
	protected Class<?> getBeanClass() {
		return OpenTSDBReporterFactoryBean.class;
	}
	
	protected void validate(ValidationContext c) {
		c.require(PERIOD, DURATION_STRING_REGEX, "Period is required and must be in the form '\\d+(ns|us|ms|s|m|h|d)'");

		c.optional(CLOCK_REF);
		c.optional(OUTPUT_REF);


		c.optional(RATE_UNIT, TIMEUNIT_STRING_REGEX, "Rate unit must be one of the enum constants from java.util.concurrent.TimeUnit");
		c.optional(DURATION_UNIT, TIMEUNIT_STRING_REGEX, "Duration unit must be one of the enum constants from java.util.concurrent.TimeUnit");

		c.optional(FILTER_PATTERN);
		c.optional(FILTER_REF);
		if (c.has(FILTER_PATTERN) && c.has(FILTER_REF)) {
			c.reject(FILTER_REF, "Reporter element must not specify both the 'filter' and 'filter-ref' attributes");
		}

		c.rejectUnmatchedProperties();
	}
	

}
