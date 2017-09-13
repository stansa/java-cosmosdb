/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stansa.flightstatus;



import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Stansa
 */
// tag::code[]
//@Data
public class flightstatus {


	private String id;
	private String forecastedarrtime;
	private String schedulearrtime;



	private flightstatus() {}

	public flightstatus(String id, String forecastedarrtime) {
		this.id = id;
		this.forecastedarrtime = forecastedarrtime;

	}

	public String getId() {
		return this.id;
	}


	public String getForecastedarrtime() {
		return forecastedarrtime;
	}

	public void getForecastedarrtime(String forecastedarrtime) {
		 this.forecastedarrtime = forecastedarrtime;
	}


	public String getSchedulearrtime() {
		return schedulearrtime;
	}

	public void setSchedulearrtime(String schedulearrtime) {
		this.schedulearrtime = schedulearrtime;
	}

}
// end::code[]