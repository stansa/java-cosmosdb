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

import com.microsoft.azure.documentdb.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
// tag::code[]
@RestController
public class FlightStatusRestController {

    @Autowired
    private final flightstatusRepository repository = null;

	@RequestMapping(value = "/api/pollflights")
	public  List<flightstatus> redyes() {

        final List<flightstatus> result = new ArrayList<flightstatus>();

        RedyeDao dao = new RedyeDao();

        Document d = dao.getDocumentById("WN335", "201793");
        flightstatus fs = new flightstatus((String)d.get("id"), (String)d.get("forecastedarrtime"));
        fs.setSchedulearrtime((String)d.get("schedulearrtime"));
		result.add(fs);

		//d = dao.getDocumentById("WN11", "201793");
		//fs = new flightstatus((String)d.get("id"), (String)d.get("forecastedarrtime"));
		//fs.setSchedulearrtime((String)d.get("schedulearrtime"));
		//result.add(fs);



		return result;
	}


}
// end::code[]
