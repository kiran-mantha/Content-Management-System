package com.example.cms.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.exception.ContributionPanelNotFoundByIdException;
import com.example.cms.exception.UnAuthorizedException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.model.ContributionPanel;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributionPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.service.PanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PanelServiceImpl implements PanelService {
	
	private ContributionPanelRepository pr;
	private BlogRepository br;
	private UserRepository ur;
	private ResponseStructure<ContributionPanel> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> addUser(int userId, int panelId) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return ur.findByEmail(username).map(owner -> {
			
			return pr.findById(panelId).map(panel -> {
				
				if(!br.existsByUserAndPanel(owner, panel)) throw new UnAuthorizedException("Illegal accept request");
				
				return ur.findById(userId).map(contributor -> {
					
					if(!pr.existsByContributors(contributor)) panel.getContributors().add(contributor);
					pr.save(panel);
					
					return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
															  .setMessage("User Inserted successfully!")
															  .setData(panel));
				}).orElseThrow(() -> new UserNotFoundByIdException("Cannot Insert contributor"));
			}).orElseThrow(() -> new ContributionPanelNotFoundByIdException("Cannot Insert contributor"));
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> deleteUser(int userId, int panelId) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return ur.findByEmail(username).map(owner -> {
			
			return pr.findById(panelId).map(panel -> {
				
				if(!br.existsByUserAndPanel(owner, panel)) throw new UnAuthorizedException("Illegal accept request");
				
				return ur.findById(userId).map(contributor -> {
					if(panel.getContributors().contains(contributor)) panel.getContributors().remove(contributor);
					pr.save(panel);
					
					return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
															  .setMessage("User deleted successfully!")
															  .setData(panel));
				}).orElseThrow(() -> new UserNotFoundByIdException("Cannot delete contributor"));
			}).orElseThrow(() -> new ContributionPanelNotFoundByIdException("Cannot delete contributor"));
		}).get();
	}

}
