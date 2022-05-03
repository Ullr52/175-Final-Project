package dmacc.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Apr 16, 2022
 */
@Data
@Entity
public class UserInformation {
	
	@Id
	@GeneratedValue
	

	private long id;
	private String userName;
	private String fName;
	private String lName;
	private String streetAddress;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNum;
	private String password;
	private boolean loginActive;
	
	@ManyToMany
	
	private List<ComicBookInformation> selectedComics;
	
	public String logOut() {
		if(isLoginActive() == false) {
			return "User already logged out";
		}
		else {
			loginActive = false;
			return "User logged out";
		}
	}
	
	public String logIn(String password) {
		if(isLoginActive() == false) {
			if(password == getPassword()) {
				loginActive = true;
			return "User has been logged in!";
			}
			else {
				return "Incorrect password!";
			}
		}
		else {
			return "User already logged in!";
		}
	}
	
	public UserInformation(long id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}
	
	public UserInformation(long id, String userName, String password, List<ComicBookInformation> selectedComics) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.selectedComics = selectedComics;
	}

	/**
	 * 
	 */
	public UserInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
