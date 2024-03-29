package de.htw.ai.kbe.di;

import java.io.File;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.auth.StandardUserRegistry;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongDatabase;
import de.htw.ai.kbe.songs.SongEntry;
import de.htw.ai.kbe.songs.SongFileDatabase;
import de.htw.ai.kbe.user.IUserRegistry;

public class DependencyBinder extends AbstractBinder {
	
	public static final String PERSISTANCE_UNIT_NAME = "_s0559090__songsdb";
	
	@Override
	protected void configure()
	{
		SongFileDatabase.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/songsOld.json";
		StandardUserRegistry.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/user.json";
		
		if(SongFileDatabase.defaultPathAvailable())
			bind(SongFileDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
		else
			bind(SongDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
	
		
		bind(StandardUserRegistry.class).to(new TypeLiteral<IUserRegistry<StandardUser>>(){}).in(Singleton.class);
		
		bind(Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME)).to(EntityManagerFactory.class);
	}
}
