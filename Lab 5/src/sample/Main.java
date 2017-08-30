package sample;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Interact with Database Lab!");

        MusicAlbumDSC musicAlbumDSC = new MusicAlbumDSC();
        howManyRecords(musicAlbumDSC);

        create(musicAlbumDSC);

        read(musicAlbumDSC);

        update(musicAlbumDSC);

        delete(musicAlbumDSC);

        listAllCompilation(musicAlbumDSC);

        listAllRockGenre(musicAlbumDSC);

        list(musicAlbumDSC);
        howManyRecords(musicAlbumDSC);
    }

    private static void howManyRecords(MusicAlbumDSC musicAlbumDSC)
    {
        try
        {
            System.out.println("Music Album record count is " + musicAlbumDSC.count());
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static void list(MusicAlbumDSC musicAlbumDSC)
    {
        try
        {
            List<MusicAlbum> albumList = musicAlbumDSC.getAll();
            System.out.println("\n\n------- ALL ALBUMS -------");
            for (MusicAlbum ma : albumList)
                System.out.println(ma);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    // todo in lab
    private static void create(MusicAlbumDSC musicAlbumDSC)
    {
        MusicAlbum ma = new MusicAlbum("a42","Test","Rap",false,4);


        System.out.println("\n\n------- ADDING NEW ALBUM -------");

        musicAlbumDSC.add(ma);
    }

    private static void read(MusicAlbumDSC musicAlbumDSC)
    {
        System.out.println("\n\n------- READING AN ALBUM -------");
        try {
            MusicAlbum ma = musicAlbumDSC.get("a20");
            System.out.println("found Album : " + ma);
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // todo in lab
    private static  void update(MusicAlbumDSC musicAlbumDSC)
    {
        System.out.println("\n\n------- UPDATING AN ALBUM -------");
        try {
            MusicAlbum ma = musicAlbumDSC.get("a49");
            ma.setGenre("Country");
            musicAlbumDSC.update(ma);
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // todo in lab
    private static  void delete(MusicAlbumDSC musicAlbumDSC)
    {
        System.out.println("\n\n------- DELETING AN ALBUM -------");
        try {
            musicAlbumDSC.remove("a42");
        }
        catch (Exception e)
        {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // todo in lab
    private static void listAllCompilation(MusicAlbumDSC musicAlbumDSC)
    {
        System.out.println("\n\n------- ALL COMPILATION ALBUMS -------");
        List<MusicAlbum> albumList = musicAlbumDSC.findByCompilation(true);
        for (MusicAlbum ma : albumList)
            System.out.println(ma);
    }

    // todo in lab
    private static void listAllRockGenre(MusicAlbumDSC musicAlbumDSC)
    {
        System.out.println("\n\n------- ALL ROCK ALBUMS -------");
        List<MusicAlbum> albumList = musicAlbumDSC.findByGenre("Rock");
        for (MusicAlbum ma : albumList)
            System.out.println(ma);
    }
}
