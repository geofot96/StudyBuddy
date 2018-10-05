package ch.epfl.sweng.studdybuddy;

public class Course<X, Y, Z>
{
    private final X couseName;
    private final Y language;
    private final Z section;

    public Course(X couseName, Y language, Z section)
    {
        this.couseName = couseName;
        this.language = language;
        this.section = section;
    }

    public X getCourseName()
    {
        return couseName;
    }

    public Y getLanguage()
    {
        return language;
    }

    public Z getSection()
    {
        return section;
    }
}
