package com.muktalabs.weatheranalysis.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.muktalabs.weatheranalysis.monthly.mapred.RdiCalculations;
import com.muktalabs.weatheranalysis.util.StationRetrival;

@RestController
@RequestMapping("/rdi")
public class RDIController {
	private static final Logger logger = Logger.getLogger(RDIController.class
			.getName());
	
	@RequestMapping(value = "/get/{stationCode}/{year}", method = RequestMethod.GET)
	protected @ResponseBody RDIResult getRDI(@PathVariable int stationCode,
			@PathVariable int year) throws Exception {
		RdiCalculations calculations=new RdiCalculations();
		RDIResult result = new RDIResult("SUCCESS", 0);
	
			double rdi = calculations.normalizedrdi(stationCode, year);
			result.setRdi(rdi);
			logger.info("RDI For station=" + stationCode + ", year=" + year
					+ ": " + result);

		
		return result;
	}

	@RequestMapping(value = "/getStations", method = RequestMethod.GET)
	protected @ResponseBody List<StationPixel> getStations()
			throws FileNotFoundException {

		List<StationPixel> al = new ArrayList<StationPixel>();
		List<String> lis = new ArrayList<String>();
		lis = new StationRetrival().getfile();

		for (String f : lis) {
			String[] p = f.split(",");

			int m = Integer.parseInt(p[0]);
			if (m >= 10) {
				StationPixel stpix = new StationPixel(p[1], p[3], p[4]);
				/*
				 * stpix.setStationCode(p[1]); stpix.setX(p[3]);
				 * stpix.setY(p[4]);
				 */

				al.add(stpix);

			}

		}
		return al;
	}
}
