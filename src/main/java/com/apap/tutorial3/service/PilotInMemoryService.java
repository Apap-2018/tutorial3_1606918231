package com.apap.tutorial3.service;

import com.apap.tutorial3.model.PilotModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class PilotInMemoryService implements PilotService{
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}
	
	@Override
	public List<PilotModel> getListPilot(){
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		for(PilotModel p : archivePilot) {
			if (licenseNumber.equals(p.getLicenseNumber())) {
				return p;
			}
		}
		return null;
	}

	@Override
	public PilotModel getPilotDetailById(String id) {
		for(PilotModel p : archivePilot) {
			if (id.equals(p.getId())) {
				return p;
			}
		}
		return null;
	}
}
