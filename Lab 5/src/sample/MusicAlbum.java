package sample;

public class MusicAlbum implements HasKey<String>
{
	private String id;
	private String name;
	private String genre;
	private boolean isCompilation;
	private int trackCount;

	public MusicAlbum() {}
	public MusicAlbum(String id, String name, String genre, boolean isCompilation, int trackCount)
	{
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.isCompilation = isCompilation;
		this.trackCount = trackCount;
	}

	public void setCompilation(boolean isCompilation) {
		this.isCompilation = isCompilation;
	}

	public void setTrackCount(int trackCount) {
		this.trackCount = trackCount;
	}

	@Override
	public String getKey()
	{
		return getId();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public boolean isCompilation()
	{
		return isCompilation;
	}

	public int getTrackCount()
	{
		return trackCount;
	}

	@Override
	public String toString() {
		return "MusicAlbum{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", genre='" + genre + '\'' +
				", isCompilation=" + isCompilation +
				", trackCount=" + trackCount +
				'}';
	}

	public boolean equals(Object obj)
	{
		return
			obj != null &&
			obj instanceof MusicAlbum &&
			((MusicAlbum)obj).id == this.id;
	}
}
