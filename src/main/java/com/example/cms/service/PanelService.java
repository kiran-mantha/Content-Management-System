package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.model.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface PanelService {

	ResponseEntity<ResponseStructure<ContributionPanel>> addUser(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId);

}
