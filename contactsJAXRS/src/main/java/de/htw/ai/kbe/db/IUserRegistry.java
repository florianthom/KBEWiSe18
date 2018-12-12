package de.htw.ai.kbe.db;

import java.util.List;

public interface IUserRegistry<T extends User>
{
	void add(T user);
	T get(int id);
	T remove(int id);
	void delete(int id);
	List<T> users();
	String authorize(int id);
	void unauthorize(String id);
	boolean authorized(String token);
	T byUserId(String userId);
}
