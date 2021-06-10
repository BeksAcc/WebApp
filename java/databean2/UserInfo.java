package databean2;

import org.genericdao.PrimaryKey;

@PrimaryKey("email")
public class UserInfo {
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private String birthDate;
	private String avatar;
	
	public String getEmail() 		{return email;}
	public String getFirstName() 	{return firstName;}
	public String getLastName() 	{return lastName;}
	public String getGender()		{return gender;}
	public String getBirthDate() 	{return birthDate;}
	public String getAvatar() 		{return avatar;}
	
	public void setEmail(String email) 				{this.email = email;}
	public void setFirstName(String firstName) 		{this.firstName = firstName;}
	public void setLastName(String lastName) 		{this.lastName = lastName;}
	public void setGender(String gender) 			{this.gender = gender;}
	public void setBirthDate(String birthDate) 		{this.birthDate = birthDate;}
	public void setAvatar(String avatar) 			{this.avatar = avatar;}
}

