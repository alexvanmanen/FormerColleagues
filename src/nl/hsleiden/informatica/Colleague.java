package nl.hsleiden.informatica;

import java.util.Date;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Colleague {
	public String getMyPersonalEmail() throws NotImplementedException;
	public Date getMyBirthDate() throws NotImplementedException;
	public String getFarewellMessage() throws NotImplementedException;
	public String getWishForTheFuture() throws NotImplementedException;
	
}
