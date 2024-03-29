// SongDAO for in-memory-db

package de.htw.ai.kbe.songs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import de.htw.ai.kbe.db.Entry;
import de.htw.ai.kbe.db.IDatabase;

public class SongDatabase implements IDatabase<SongEntry, Song>
{
	private ConcurrentHashMap<Integer, Entry<Song>> data;

	public SongDatabase()
	{
		super();
		this.data = new ConcurrentHashMap<>();
	}

	@Override
	public void insert(Entry<Song> entry)
	{
		if(!entry.hasId() || data.containsKey(entry.getId()))
		{
			int id = 0;
			
			while(this.data.keySet().contains(Integer.valueOf(id)))
				++id;
			
			entry.setId(id);
		}
		
		this.data.put(Integer.valueOf(entry.getId()), entry);
	}

	@Override
	public Entry<Song> retrieve(int id)
	{
		return this.data.get(Integer.valueOf(id));
	}
	
	@Override
	public void replace(Entry<Song> entryOld, Entry<Song> entryNew)
	{
		if(entryOld.hasId() && data.containsKey(entryOld.getId()))
			this.data.replace(Integer.valueOf(entryOld.getId()), entryNew);
	}
	
	@Override
	public boolean exists(int id)
	{
		return retrieve(Integer.valueOf(id)) != null;
	}
	
	@Override
	public void add(Song value)
	{
		if(value == null)
			return;
		
		Entry<Song> entry = new SongEntry(value);
		this.insert(entry);
	}

	@Override
	public Song get(int id)
	{
		Entry<Song> entry = this.retrieve(id);
		
		if(entry == null)
			return null;
		
		return entry.retrieve();
	}

	@Override
	public void delete(int id)
	{
		this.remove(id);
	}
	
	@Override
	public Song remove(int id)
	{
		Entry<Song> entry = this.data.remove(Integer.valueOf(id));
		
		if(entry == null)
			return null;
		
		return entry.retrieve();
	}
	
	@Override
	public void update(int id, Song value)
	{
		replace(retrieve(id), new SongEntry(id, value));
	}

	@Override
	public int size()
	{
		return this.data.size();
	}
	
	@Override
	public void clear()
	{
		this.data.clear();
	}

	@Override
	public List<Song> values()
	{
		System.out.println("check2");

		List<Song> list = new ArrayList<>();
		
		for(Entry<Song> entry : this.data.values())
			list.add(entry.retrieve());
		System.out.println("check3");

		return list;
	}

	@Override
	public List<Entry<Song>> entries()
	{
		return new ArrayList<Entry<Song>>(this.data.values());
	}

	@Override
	public String toJson()
	{
		Entry<Song> base = new SongEntry();
		return base.entriesToJson(this.entries());
	}

	@Override
	public void fromJson(String json)
	{
		Entry<Song> base = new SongEntry();
		List<Entry<Song>> list = base.entriesFromJson(json);
		
		if(list == null)
			return;
		
		for(Entry<Song> entry : list)
			insert(entry);
	}

	@Override
	public String toXml()
	{
		Entry<Song> base = new SongEntry();
		return base.entriesToXml(this.entries());
	}

	@Override
	public void fromXml(String xml)
	{
		Entry<Song> base = new SongEntry();
		List<Entry<Song>> list = base.entriesFromXml(xml);
		
		if(list == null)
			return;
		
		for(Entry<Song> entry : list)
			insert(entry);
	}
}
