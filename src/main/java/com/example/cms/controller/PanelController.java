package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.model.ContributionPanel;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PanelController {

	private PanelService panelService;
	
	@PutMapping("/user/{userId}/contribution-panel/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> addUser(@PathVariable int userId, @PathVariable int panelId) {
		return panelService.addUser(userId, panelId);
	}
	
	@DeleteMapping("/user/{userId}/contribution-panel/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(@PathVariable int userId, @PathVariable int panelId) {
		return panelService.deleteUser(userId, panelId);
	}
}
