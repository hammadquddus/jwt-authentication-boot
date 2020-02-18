package com.virtualpairprogrammers.roombooking.model.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "name cannot be blank.")
	private String name;

	@JsonIgnore
	@NotBlank(message = "password cannot be blank.")
	private String password;

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "id"))
	private Set<String> authorities = new HashSet();

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<String> grantedAuthorities) {
		this.authorities = grantedAuthorities;
	}

	public List<GrantedAuthority> getGrantedAuthorities() {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		this.authorities.forEach((role) -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(role));
		});
		
		return grantedAuthorities;
	}

	public void addAuthority(String authority) {
		this.authorities.add(authority);
	}
	
	public boolean removeAuthority(String authority) {
		return this.authorities.remove(authority);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", authorities=" + authorities + "]";
	}


}
