package com.xqs.remote;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PermissionContext implements Serializable {
	private static final long serialVersionUID = -2146129737182962451L;

	private Set<String> roles;

	private Set<String> permissions;
}
