package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;


@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value= "flyHour", required = true) int flyHour
			)
	{
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getListPilot();
		
		if (archive.isEmpty()) return "error";
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping(value = {"/pilot/view/","pilot/view/license-number/{licenseNumber}"})
	public String viewPath(@PathVariable String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		if (pilot == null) return "error";
		
		model.addAttribute("pilot", pilot);
		return "view-pilot";
	}
	
	@RequestMapping(value = {"/pilot/update/","pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}"})
	public String updateFlyHour(
			@PathVariable("licenseNumber") String licenseNumber,
			@PathVariable("flyHour") int flyHour,
			Model model) {
		
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if (pilot == null) return "error";
		
		model.addAttribute("oldFlyHour", pilot.getFlyHour());
		
		pilot.setFlyHour(flyHour);
		model.addAttribute("pilot", pilot);
		return "update";
	}
	
	@RequestMapping(value = {"/pilot/delete/id/","pilot/delete/id/{id}"})
	public String deletePilot(
			@PathVariable("id") String id,
			Model model) {
		
		//Saya menambahkan getPilotDetailById pada interface PilotService
		PilotModel pilot = pilotService.getPilotDetailById(id);
		
		if (pilot == null) return "error";
		
		List<PilotModel> archive = pilotService.getListPilot();
		for (PilotModel p : archive) {
			if (p.getId().equals(pilot.getId())) {
				archive.remove(p);
				break;
			}
		}
		model.addAttribute("pilot", pilot);
		return "delete";
	}
}
