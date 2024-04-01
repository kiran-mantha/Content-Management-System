package com.example.cms.requestdto;

import java.util.ArrayList;
import java.util.List;

import com.example.cms.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContributionPanelRequest {

	private int panelId;
	private List<User> contributors = new ArrayList<User>();
}
