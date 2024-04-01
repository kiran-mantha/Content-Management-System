package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.model.ContributionPanel;
import com.example.cms.model.User;

public interface ContributionPanelRepository extends JpaRepository<ContributionPanel, Integer> {

	boolean existsByContributors(User contributor);
	
	boolean existsByPanelIdAndContributors(int panelId, User Contributor);
}
