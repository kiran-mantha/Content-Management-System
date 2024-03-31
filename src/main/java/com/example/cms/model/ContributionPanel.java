package com.example.cms.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContributionPanel {

	@Id
	private int panelId;
	private List<User> users = new ArrayList<User>();
}
