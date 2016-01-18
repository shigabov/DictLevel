package ru.asbvapps.android.dictlevel;
/**
 * Created by Артем on 31.10.2015.
 */
public class Word {
    public int popularity;
    public String name;

    public Word(int popularity, String name){
        this.popularity = popularity;
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;//?
        int result = 1;
        result = prime * result + this.popularity;
        result = prime * result + this.name.hashCode();
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
        Word other = (Word) obj;
        if (popularity != other.popularity)
            return false;
        if (!name.equals(other.name))
            return false;
        return true;
    }
}
