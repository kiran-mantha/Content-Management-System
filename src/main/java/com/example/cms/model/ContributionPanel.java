package com.example.cms.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContributionPanel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int panelId;
	@ManyToMany
	private List<User> contributors = new ArrayList<User>();
}
